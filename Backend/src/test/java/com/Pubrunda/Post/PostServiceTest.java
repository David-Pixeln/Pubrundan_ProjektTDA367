package com.Pubrunda.Post;

import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.post.PostService;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.exception.AuthorizationException;
import jakarta.transaction.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

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

        LocalDateTime dateTime1 = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.JUNE, 29, 19, 30, 40);
        postRepository.save(new Post(testUser1, dateTime1, "imagePlaceholder"));
        postRepository.save(new Post(testUser2, dateTime2, "imagePlaceholder"));
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void getAllPostsShouldReturnTwoPostsWithCorrectId() {
        List<Post> posts = postService.getAllPosts(new PostQueryParams());

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getId()).isEqualTo(postRepository.findAll().get(0).getId());
        assertThat(posts.get(1).getId()).isEqualTo(postRepository.findAll().get(1).getId());
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
        LocalDateTime dateTime = LocalDateTime.of(2015, Month.AUGUST, 29, 19, 30, 40);
        Post post = new Post(testUser, dateTime, "imagePlaceholder");

        postService.createPost(testUser, post);

        assertThat(postRepository.findAll()).isNotEmpty();
        assertThat(postRepository.findAll()).hasSize(3);
        assertThat(post.getId()).isEqualTo(postRepository.findAll().get(2).getId());

    }

    @Test
    @Transactional
    public void deletePostShouldRemovePostFromDatabase() {
        List<Post> posts = postRepository.findAll();

        System.out.println(posts);

        User testUser = userRepository.findAll().getFirst();
        Post post = new Post(testUser, LocalDateTime.now(), "imagePlaceholder");
        postRepository.save(post);

        System.out.println(postRepository.findAll());

        long postId = postRepository.findAll().getLast().getId();
        postService.deletePost(testUser, postId);

        assertThat(postRepository.findAll()).isNotEmpty();
        assertThat(postRepository.findAll()).hasSize(2);
        assertThat(postRepository.findAll().stream().allMatch(postArgument -> postArgument.getId() != postId)).isTrue();
    }

    @Test
    public void removePostShouldNotRemoveUser() {
        postRepository.deleteAll();

        assertThat(postRepository.findAll()).isEmpty();
        assertThat(userRepository.findAll()).isNotEmpty();
        assertThat(userRepository.findAll()).hasSize(2);
    }

    @Test
    public void removeUserShouldRemoveAllUserPosts() {
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