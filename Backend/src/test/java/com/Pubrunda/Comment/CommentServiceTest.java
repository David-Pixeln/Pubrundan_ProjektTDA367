package com.Pubrunda.Comment;

import com.Pubrunda.ServiceTest;
import com.Pubrunda.entities.comment.Comment;
import com.Pubrunda.entities.comment.CommentRepository;
import com.Pubrunda.entities.comment.CommentService;
import com.Pubrunda.entities.comment.dto.request.CommentQueryParams;
import com.Pubrunda.entities.comment.dto.request.CreateCommentDTO;
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

public class CommentServiceTest extends ServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

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
    /*
     * GET
     */

    @Test
    public void getAllCommentsShouldReturnThreeCommentsWithCorrectId() {
        List<Comment> allComments = commentRepository.findAll();

        List<Comment> comments = commentService.getAllComments();

        assertThat(comments).isNotEmpty();
        assertThat(comments).hasSize(3);

        for (int i = 0; i < 3; i++) {
            assertThat(comments.get(i).getId()).isEqualTo(allComments.get(i).getId());
        }
    }

    @Test
    public void getAllCommentsWithQueryParameterForAuthorIdShouldReturnTwoCommentsFromAuthor() {
        User author = userRepository.findAll().get(1);
        List<Comment> authorComments = getCommentFromAuthor(author);

        CommentQueryParams commentQueryParamForAuthor = CommentQueryParams.builder().authorId(author.getId()).build();
        List<Comment> comments = commentService.getAllComments(commentQueryParamForAuthor);

        assertThat(comments).isNotEmpty();
        assertThat(comments).hasSize(2);
        assertThat(comments).isEqualTo(authorComments);
    }
    @Test
    public void getAllCommentsWithQueryParameterForAuthorUsernameShouldReturnTwoCommentsFromAuthor() {
        User author = userRepository.findAll().get(1);
        List<Comment> authorPosts = getCommentFromAuthor(author);

        CommentQueryParams commentQueryParamForAuthor = CommentQueryParams.builder().authorUsername(author.getUsername()).build();
        List<Comment> comments = commentService.getAllComments(commentQueryParamForAuthor);

        assertThat(comments).isNotEmpty();
        assertThat(comments).hasSize(2);
        assertThat(comments).isEqualTo(authorPosts);
    }

    @Test
    public void getAllCommentsWithQueryParameterForBeforeShouldReturnAllCommentsBeforeTime() {
        Comment firstComment = commentRepository.findAll().getFirst();
        Comment secondComment = commentRepository.findAll().get(1);

        CommentQueryParams commentQueryParamForBefore = CommentQueryParams.builder().before(LocalDateTime.MAX.withYear(2015)).build();
        List<Comment> comments = commentService.getAllComments(commentQueryParamForBefore);

        assertThat(comments).isNotEmpty();
        assertThat(comments).hasSize(2);
        assertThat(comments.getFirst()).isEqualTo(firstComment);
        assertThat(comments.getLast()).isEqualTo(secondComment);
    }


    @Test
    public void getAllCommentsWithQueryParameterForAfterShouldReturnAllCommentsAfterTime() {
        Comment secondComment = commentRepository.findAll().get(1);
        Comment thirdComment = commentRepository.findAll().get(2);

        CommentQueryParams commentQueryParamForAfter = CommentQueryParams.builder().after(LocalDateTime.MIN.withYear(2015)).build();
        List<Comment> comments = commentService.getAllComments(commentQueryParamForAfter);

        assertThat(comments).isNotEmpty();
        assertThat(comments).hasSize(2);
        assertThat(comments.getFirst()).isEqualTo(secondComment);
        assertThat(comments.getLast()).isEqualTo(thirdComment);
    }

    @Test
    public void getCommentByIdShouldReturnOneCommentWithCorrectId() {
        long commentId = commentRepository.findAll().getFirst().getId();

        Comment comment = commentService.getCommentById(commentId);
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(commentId);
    }

    /*
     * POST
     */

    @Test
    public void createCommentShouldAddCommentToDatabase() {
        int commentCountBefore = commentRepository.findAll().size();
        User author = userRepository.findAll().getFirst();
        CreateCommentDTO newComment = new CreateCommentDTO("contentPlaceholder");

        Comment comment = commentService.createComment(author, newComment);

        List<Comment> allComments = commentRepository.findAll();

        assertThat(allComments).isNotEmpty();
        assertThat(allComments).hasSize(commentCountBefore + 1);
        assertThat(comment.getId()).isEqualTo(allComments.getLast().getId());
    }

    @Test(expected = AuthorizationException.class)
    public void nonAuthorizedUserShouldNotBeAbleToDeleteOtherUsersComments() {
        User authorizedUser = userRepository.findAll().getFirst();
        Comment commentByAuthorizedUser = getCommentFromAuthor(authorizedUser).getFirst();

        User nonAuthorizedUser = userRepository.findAll().get(1);

        commentService.deleteComment(nonAuthorizedUser, commentByAuthorizedUser.getId());
    }

    /*
     * DELETE
     */

    @Test
    public void deleteCommentShouldRemoveCommentFromDatabase() {
        User user = userRepository.findAll().getFirst();
        Comment existingComment = getCommentFromAuthor(user).getFirst();

        long existingCommentId = existingComment.getId();
        int commentCountBefore = commentRepository.findAll().size();

        commentService.deleteComment(user, existingCommentId);

        List<Comment> allComments = commentRepository.findAll();

        assertThat(allComments).isNotEmpty();
        assertThat(allComments).hasSize(commentCountBefore - 1);
        assertThat(allComments.stream().allMatch(comment -> comment.getId() != existingCommentId)).isTrue();
    }

    private List<Comment> getCommentFromAuthor(User author) {
        return commentRepository.findAll().stream().filter(comment -> comment.getAuthor().equals(author)).toList();
    }



}
