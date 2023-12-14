package com.Pubrunda.Post;

import com.Pubrunda.ServiceTest;
import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.post.PostService;
import com.Pubrunda.entities.post.dto.request.CreatePostDTO;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.exception.AuthorizationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostServiceTest extends ServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;


    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);

        userRepository.saveAll(List.of(testUser1, testUser2));

        LocalDateTime dateTime1 = LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5);
        LocalDateTime dateTime3 = LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15);
        postRepository.save(new Post(testUser1, dateTime1, "imagePlaceholder"));
        postRepository.save(new Post(testUser2, dateTime2, "imagePlaceholder"));
        postRepository.save(new Post(testUser2, dateTime3, "imagePlaceholder"));
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void getAllPostsShouldReturnThreePostsWithCorrectId() {
        List<Post> allPosts = postRepository.findAll();

        List<Post> posts = postService.getAllPosts();

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(3);

        for (int i = 0; i < 3; i++) {
            assertThat(posts.get(i).getId()).isEqualTo(allPosts.get(i).getId());
        }
    }

    @Test
    public void getAllPostsWithUserQueryParameterForIdShouldReturnPostWithCorrectId() {
        Post firstPost = postRepository.findAll().getFirst();
        PostQueryParams postQueryParamForId = PostQueryParams.builder().id(firstPost.getId()).build();

        List<Post> posts = postService.getAllPosts(postQueryParamForId);

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(1);
        assertThat(posts.getFirst()).isEqualTo(firstPost);
    }

    @Test
    public void getAllPostsWithPostQueryParameterForAuthorIdShouldReturnTwoPostsFromAuthor() {
        User author = userRepository.findAll().get(1);
        List<Post> authorPosts = getPostFromAuthor(author);

        PostQueryParams postQueryParamForAuthor = PostQueryParams.builder().authorId(author.getId()).build();
        List<Post> posts = postService.getAllPosts(postQueryParamForAuthor);

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts).isEqualTo(authorPosts);
    }

    @Test
    public void getAllPostsWithPostQueryParameterForAuthorUsernameShouldReturnTwoPostsFromAuthor() {
        User author = userRepository.findAll().get(1);
        List<Post> authorPosts = getPostFromAuthor(author);

        PostQueryParams postQueryParamForAuthor = PostQueryParams.builder().authorUsername(author.getUsername()).build();
        List<Post> posts = postService.getAllPosts(postQueryParamForAuthor);

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts).isEqualTo(authorPosts);
    }

    @Test
    public void getAllPostsWithPostQueryParameterForBeforeShouldReturnAllPostsBeforeTime() {
        Post firstPost = postRepository.findAll().getFirst();
        Post secondPost = postRepository.findAll().get(1);

        PostQueryParams postQueryParamForBefore = PostQueryParams.builder().before(LocalDateTime.MAX.withYear(2015)).build();
        List<Post> posts = postService.getAllPosts(postQueryParamForBefore);

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts.getFirst()).isEqualTo(firstPost);
        assertThat(posts.getLast()).isEqualTo(secondPost);
    }


    @Test
    public void getAllPostsWithPostQueryParameterForAfterShouldReturnAllPostsAfterTime() {
        Post secondPost = postRepository.findAll().get(1);
        Post thirdPost = postRepository.findAll().get(2);

        PostQueryParams postQueryParamForAfter = PostQueryParams.builder().after(LocalDateTime.MIN.withYear(2015)).build();
        List<Post> posts = postService.getAllPosts(postQueryParamForAfter);

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts.getFirst()).isEqualTo(secondPost);
        assertThat(posts.getLast()).isEqualTo(thirdPost);
    }

    @Test
    public void getPostByIdShouldReturnOnePostWithCorrectId() {
        long postId = postRepository.findAll().getFirst().getId();

        Post post = postService.getPostById(postId);

        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(postId);
    }

    @Test
    public void createPostShouldAddPostToDatabase() {
        int postCountBefore = postRepository.findAll().size();
        User author = userRepository.findAll().getFirst();
        CreatePostDTO newPost = new CreatePostDTO("imagePlaceholder", "contentPlaceholder");

        Post post = postService.createPost(author, newPost);

        List<Post> allPosts = postRepository.findAll();

        assertThat(allPosts).isNotEmpty();
        assertThat(allPosts).hasSize(postCountBefore + 1);
        assertThat(post.getId()).isEqualTo(allPosts.getLast().getId());
    }

    @Test(expected = AuthorizationException.class)
    public void nonAuthorizedUserShouldNotBeAbleToDeleteOtherUsersPosts() {
        User authorizedUser = userRepository.findAll().getFirst();
        Post postByAuthorizedUser = getPostFromAuthor(authorizedUser).getFirst();

        User nonAuthorizedUser = userRepository.findAll().get(1);

        postService.deletePost(nonAuthorizedUser, postByAuthorizedUser.getId());
    }

    @Test
    public void deletePostShouldRemovePostFromDatabase() {
        User user = userRepository.findAll().getFirst();
        Post existingPost = getPostFromAuthor(user).getFirst();

        long existingPostId = existingPost.getId();
        int postCountBefore = postRepository.findAll().size();

        postService.deletePost(user, existingPostId);

        List<Post> allPosts = postRepository.findAll();

        assertThat(allPosts).isNotEmpty();
        assertThat(allPosts).hasSize(postCountBefore - 1);
        assertThat(allPosts.stream().allMatch(post -> post.getId() != existingPostId)).isTrue();
    }

    private List<Post> getPostFromAuthor(User author) {
        return postRepository.findAll().stream().filter(post -> post.getAuthor().equals(author)).toList();
    }

}