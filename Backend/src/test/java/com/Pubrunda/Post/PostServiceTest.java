package com.Pubrunda.Post;

import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.post.PostService;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.exception.AuthorizationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PostServiceTest {

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

        userRepository.save(testUser1);
        userRepository.save(testUser2);

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
    public void getAllPostsShouldReturnTwoPostsWithCorrectId() {
        List<Post> posts = postService.getAllPosts();

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(3);
        assertThat(posts.get(0).getId()).isEqualTo(postRepository.findAll().get(0).getId());
        assertThat(posts.get(1).getId()).isEqualTo(postRepository.findAll().get(1).getId());
        assertThat(posts.get(2).getId()).isEqualTo(postRepository.findAll().get(2).getId());
    }

    @Test
    public void getAllPostsWithUserQueryParameterForIdShouldReturnPostWithCorrectId() {
        Post firstPost = postRepository.findAll().getFirst();
        PostQueryParams postQueryParamForId = PostQueryParams.builder().id(firstPost.getId()).build();

        assertThat(postService.getAllPosts(postQueryParamForId).getFirst()).isEqualTo(firstPost);
    }

    @Test
    public void getAllPostsWithPostQueryParameterForAuthorShouldReturnTwoPostsFromAuthor() {
        Post secondPost = postRepository.findAll().get(1);
        Post thirdPost = postRepository.findAll().get(2);

        PostQueryParams postQueryParamForAuthor = PostQueryParams.builder().author(secondPost.getAuthor()).build();

        List<Post> AuthorPosts = postService.getAllPosts(postQueryParamForAuthor);
        assertThat(AuthorPosts).hasSize(2);
        assertThat(AuthorPosts.getFirst()).isEqualTo(secondPost);
        assertThat(AuthorPosts.get(1)).isEqualTo(thirdPost);
    }

    @Test
    public void getAllPostsWithPostQueryParameterForBeforeShouldReturnPosts() {
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
    public void getAllPostsWithPostQueryParameterForAfterShouldReturnPosts() {
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
        User testUser = userRepository.findAll().getFirst();
        Post post = new Post(testUser, LocalDateTime.now(), "imagePlaceholder");

        postService.createPost(testUser, post);

        assertThat(postRepository.findAll()).isNotEmpty();
        assertThat(postRepository.findAll()).hasSize(4);
        assertThat(post.getId()).isEqualTo(postRepository.findAll().get(3).getId());
    }

    @Test
    public void deletePostShouldRemovePostFromDatabase() {
        User testUser = userRepository.findAll().getFirst();
        Post userPost = new Post(testUser, LocalDateTime.now(), "imagePlaceholder");
        postRepository.save(userPost);

        long postId = userPost.getId();
        postService.deletePost(postId);

        assertThat(postRepository.findAll()).isNotEmpty();
        assertThat(postRepository.findAll()).hasSize(3);
        assertThat(postRepository.findAll().stream().allMatch(post -> post.getId() != postId)).isTrue();
    }

    @Test
    public void deletePostShouldNotRemoveUser() {
        postRepository.deleteAll();

        assertThat(postRepository.findAll()).isEmpty();
        assertThat(userRepository.findAll()).isNotEmpty();
        assertThat(userRepository.findAll()).hasSize(2);
    }

    @Test
    public void deleteUserShouldRemoveAllUserPosts() {
        userRepository.deleteAll();

        assertThat(userRepository.findAll()).isEmpty();
        assertThat(postRepository.findAll()).isEmpty();
    }

    @Test(expected = AuthorizationException.class)
    public void nonAuthorizedUserShouldNotBeAbleToDeleteOtherUsersPosts() {
        User authorizedUser = userRepository.findAll().getFirst();
        User nonAuthorizedUser = new User("username", "password", Role.USER);
        LocalDateTime dateTime = LocalDateTime.of(2015, Month.AUGUST, 29, 19, 30, 40);
        Post post = new Post(authorizedUser, dateTime, "imagePlaceholder");
        postRepository.save(post);

        long postId = postRepository.findAll().getLast().getId();

        postService.deletePost(nonAuthorizedUser, postId);
    }
}