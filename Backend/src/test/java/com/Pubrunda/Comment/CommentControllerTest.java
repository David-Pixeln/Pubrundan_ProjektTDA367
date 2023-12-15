package com.Pubrunda.Comment;

import com.Pubrunda.ControllerTest;
import com.Pubrunda.DTOMapper;
import com.Pubrunda.entities.comment.Comment;
import com.Pubrunda.entities.comment.CommentRepository;
import com.Pubrunda.entities.comment.CommentService;
import com.Pubrunda.entities.comment.dto.request.CommentQueryParams;
import com.Pubrunda.entities.comment.dto.request.CreateCommentDTO;
import com.Pubrunda.entities.comment.dto.response.CommentDTO;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.entities.user.dto.response.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends ControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Override
    protected String getBaseUrl() {
        return super.getBaseUrl() + "/comments";
    }

    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);

        userRepository.saveAll(List.of(testUser1, testUser2));

        LocalDateTime dateTime1 = LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5);
        LocalDateTime dateTime3 = LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15);

        commentRepository.save(new Comment(testUser1, "content", dateTime1));
        commentRepository.save(new Comment(testUser2, "content", dateTime2));
        commentRepository.save(new Comment(testUser2, "content", dateTime3));
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
    public void getAllCommentsShouldReturnAllComments() throws Exception {
        List<Comment> allComments = commentRepository.findAll();
        List<User> allUsers = userRepository.findAll();
        setAuthUser(allUsers.getFirst());

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(3));

        List<com.Pubrunda.entities.comment.dto.response.CommentDTO> responseCommentList = getObjectListFromResponse(response, com.Pubrunda.entities.comment.dto.response.CommentDTO.class);

        for (int i = 0; i < allComments.size(); i++) {
            com.Pubrunda.entities.comment.dto.response.CommentDTO comment = DTOMapper.convertToDto(allComments.get(i), com.Pubrunda.entities.comment.dto.response.CommentDTO.class);
            UserDTO commentAuthor = DTOMapper.convertToDto(allComments.get(i).getAuthor(), UserDTO.class);

            com.Pubrunda.entities.comment.dto.response.CommentDTO responseComment = responseCommentList.get(i);

            assertThat(responseComment).isEqualTo(comment);
            assertThat(responseComment.getAuthor()).isEqualTo(commentAuthor);
        }
    }

    @Test
    public void getAllCommentsWithQueryParamForAuthorIdShouldReturnAllCommentsFromAuthor() throws Exception {
        User author = userRepository.findAll().get(1);
        List<Comment> existingComments = commentRepository.findAll().stream().filter(comment -> comment.getAuthor().equals(author)).toList();
        List<CommentDTO> existingCommentsDto = DTOMapper.convertToDto(existingComments, CommentDTO.class);
        setAuthUser(author);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("authorId", String.valueOf(author.getId()))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<CommentDTO> responseComments = getObjectListFromResponse(response, CommentDTO.class);

        assertThat(responseComments).isEqualTo(existingCommentsDto);
    }

    @Test
    public void getAllCommentsWithQueryParamForAuthorUsernameShouldReturnAllCommentsFromAuthor() throws Exception {
        User author = userRepository.findAll().get(1);
        List<Comment> existingComments = commentRepository.findAll().stream().filter(comment -> comment.getAuthor().equals(author)).toList();
        List<CommentDTO> existingCommentsDto = DTOMapper.convertToDto(existingComments, CommentDTO.class);
        setAuthUser(author);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("authorUsername", author.getUsername())
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<CommentDTO> responseComments = getObjectListFromResponse(response, CommentDTO.class);

        assertThat(responseComments).isEqualTo(existingCommentsDto);
    }

    @Test
    public void getAllCommentsWithQueryParamForAfterShouldReturnAllCommentsAfterASetTime() throws Exception {
        LocalDateTime queryTime = LocalDateTime.MIN.withYear(2015);
        List<Comment> commentsAfterQueryTime = commentService.getAllComments(CommentQueryParams.builder().after(queryTime).build());
        List<CommentDTO> commentsDTO = DTOMapper.convertToDto(commentsAfterQueryTime, CommentDTO.class);
        setAuthUser(userRepository.findAll().getFirst());

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("after", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<CommentDTO> responseComments = getObjectListFromResponse(response, CommentDTO.class);

        assertThat(responseComments).isEqualTo(commentsDTO);
    }

    @Test
    public void getAllCommentsWithQueryParamForBeforeShouldReturnAllCommentsBeforeASetTime() throws Exception {
        LocalDateTime queryTime = LocalDateTime.MAX.withYear(2015);
        List<Comment> commentsAfterQueryTime = commentService.getAllComments(CommentQueryParams.builder().before(queryTime).build());
        List<CommentDTO> commentsDTO = DTOMapper.convertToDto(commentsAfterQueryTime, CommentDTO.class);
        setAuthUser(userRepository.findAll().getFirst());

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("before", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<CommentDTO> responseComments = getObjectListFromResponse(response, CommentDTO.class);

        assertThat(responseComments).isEqualTo(commentsDTO);
    }

    @Test
    public void getAllCommentsUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void getCommentByIdShouldReturnOneCommentWithCorrectId() throws Exception {
        Comment firstComment = commentRepository.findAll().getFirst();
        CommentDTO firstCommentDTO = DTOMapper.convertToDto(firstComment, CommentDTO.class);

        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + firstComment.getId()));

        response.andExpect(status().isOk());

        CommentDTO responseComment = getObjectFromResponse(response, CommentDTO.class);

        assertThat(responseComment).isEqualTo(firstCommentDTO);
    }

    @Test
    public void getCommentByIdShouldReturnNotFoundIfCommentDoesNotExist() throws Exception {
        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + "/999"));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void getCommentByIdUnauthenticatedShouldReturnUnAuthorizedRequest() throws Exception {
        Comment firstComment = commentRepository.findAll().getFirst();
        long commentId = firstComment.getId();

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + commentId));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    /*
     * POST
     */

    @Test
    public void commentRequestShouldCreateAComment() throws Exception {
        User author = userRepository.findAll().getFirst();
        CreateCommentDTO newComment = new CreateCommentDTO("content");

        setAuthUser(author);
        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newComment));

        response.andExpect(status().isCreated());

        CommentDTO responseComment = getObjectFromResponse(response, CommentDTO.class);

        Comment createdComment = commentRepository.findAll().getLast();
        CommentDTO createdCommentDTO = DTOMapper.convertToDto(createdComment, CommentDTO.class);

        assertThat(responseComment).isEqualTo(createdCommentDTO);
    }

    @Test
    public void commentRequestWithMissingRequiredArgumentsShouldReturnBadRequest() throws Exception {
        User author = userRepository.findAll().getFirst();
        CreateCommentDTO newComment = new CreateCommentDTO();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newComment));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void commentRequestUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        CreateCommentDTO newComment = new CreateCommentDTO();

        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newComment));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    /*
     * DELETE
     */

    @Test
    public void deleteRequestShouldDeleteComment() throws Exception {
        User author = userRepository.findAll().getFirst();
        Comment firstComment = commentRepository.findAll().getFirst();
        long commentId = firstComment.getId();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + commentId));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void deleteOtherCommentsThanYourOwnShouldReturnForbiddenRequest() throws Exception {
        User author = userRepository.findAll().getFirst();
        Comment secondComment = commentRepository.findAll().get(1);
        long commentId = secondComment.getId();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + commentId));

        response.andExpect(status().isForbidden());
    }

    @Test
    public void deleteRequestUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        Comment firstComment = commentRepository.findAll().getFirst();
        long commentId = firstComment.getId();

        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + commentId));

        response.andExpect(status().isUnauthorized());
    }


}
