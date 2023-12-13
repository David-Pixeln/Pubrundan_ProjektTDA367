package com.Pubrunda.Post;

import com.Pubrunda.RepositoryTest;
import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRepositoryTest extends RepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


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
}
