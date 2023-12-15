package com.Pubrunda.entities.comment;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.comment.dto.request.CommentQueryParams;
import com.Pubrunda.entities.comment.dto.request.CreateCommentDTO;
import com.Pubrunda.entities.comment.dto.response.CommentDTO;
import com.Pubrunda.entities.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // READ
    @GetMapping("/{commentId}")
    public CommentDTO getCommentById(@PathVariable long commentId) {
        return DTOMapper.convertToDto(commentService.getCommentById(commentId), CommentDTO.class);
    }

    @GetMapping
    public List<CommentDTO> getAllComments(CommentQueryParams params) {
        return DTOMapper.convertToDto(commentService.getAllComments(params), CommentDTO.class);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@AuthenticationPrincipal User authenticatedUser, @RequestBody CreateCommentDTO newComment) {
        return DTOMapper.convertToDto(commentService.createComment(authenticatedUser, newComment), CommentDTO.class);
    }

    // DELETE
    @DeleteMapping("/{commentId}")
    public MessageResponse deleteComment(@AuthenticationPrincipal User authenticatedUser, @PathVariable long commentId) {
        commentService.deleteComment(authenticatedUser, commentId);
        return new MessageResponse("Post Deleted Successfully");
    }
    
}
