package com.Pubrunda.Comment;

import com.Pubrunda.RepositoryTest;
import com.Pubrunda.entities.comment.Comment;
import com.Pubrunda.entities.comment.CommentRepository;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentRepositoryTest extends RepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);

        userRepository.saveAll(List.of(testUser1, testUser2));

        LocalDateTime dateTime1 = LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5);
        LocalDateTime dateTime3 = LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15);
        commentRepository.save(new Comment(testUser1, "contentPlaceholder", dateTime1));
        commentRepository.save(new Comment(testUser2, "contentPlaceholder", dateTime2));
        commentRepository.save(new Comment(testUser2, "contentPlaceholder", dateTime3));
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    public void findAllShouldReturnAllComments() {
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isNotEmpty();
        assertThat(comments).hasSize(3);
    }

    @Test
    public void deleteCommentShouldNotRemoveUser() {
        commentRepository.deleteAll();

        assertThat(commentRepository.findAll()).isEmpty();
        assertThat(userRepository.findAll()).isNotEmpty();
        assertThat(userRepository.findAll()).hasSize(2);
    }

    @Test
    public void deleteUserShouldRemoveAllUserComments() {
        userRepository.deleteAll();

        assertThat(userRepository.findAll()).isEmpty();
        assertThat(commentRepository.findAll()).isEmpty();
    }
}
