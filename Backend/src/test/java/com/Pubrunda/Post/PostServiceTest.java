package com.Pubrunda.Post;

import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.post.PostService;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
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
        postRepository.deleteAll();
    }

    @Test
    public void testGetAllPostsShouldReturnTwoPostsWithCorrectId() {
        List<Post> posts = postService.getAll(new PostQueryParams());

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getId()).isEqualTo(postRepository.findAll().get(0).getId());
        assertThat(posts.get(1).getId()).isEqualTo(postRepository.findAll().get(1).getId());
    }

    @Test
    public void testGetPostByIdShouldReturnOnePostWithCorrectId() {
        long postId = postRepository.findAll().getFirst().getId();

        Post post = postService.getPostById(postId);

        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(postId);
    }

    @Test
    public void testCreatePostShouldAddPostToDatabase() {
        User testUser = userRepository.findAll().getFirst();
        LocalDateTime dateTime = LocalDateTime.of(2015, Month.AUGUST, 29, 19, 30, 40);
        Post post = new Post(testUser, dateTime, "imagePlaceholder");

        postService.createPost(post);

        assertThat(postRepository.findAll()).isNotEmpty();
        assertThat(postRepository.findAll()).hasSize(3);
        assertThat(post.getId()).isEqualTo(postRepository.findAll().get(2).getId());

    }

    @Test
    public void testDeletePostShouldRemovePostFromDatabase() {
        User testUser = userRepository.findAll().getFirst();
        LocalDateTime dateTime = LocalDateTime.of(2015, Month.AUGUST, 29, 19, 30, 40);
        Post post = new Post(testUser, dateTime, "imagePlaceholder");

        postRepository.save(post);
        long postId = post.getId();
        postService.deletePost(post.getId());

        assertThat(postRepository.findAll()).isNotEmpty();
        assertThat(postRepository.findAll()).hasSize(2);
        assertThat(postRepository.findAll().stream().allMatch(postArgument -> post.getId() != postId)).isTrue();
    }
}