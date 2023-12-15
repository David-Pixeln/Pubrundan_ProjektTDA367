package com.Pubrunda.entities.comment;

import com.Pubrunda.AuthorizationManager;
import com.Pubrunda.entities.comment.dto.request.CommentQueryParams;
import com.Pubrunda.entities.comment.dto.request.CreateCommentDTO;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.exception.AuthorizationException;
import com.Pubrunda.exception.MissingRequiredAttributeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment getCommentById(long commentId) {
        return commentRepository.findById(commentId).orElseThrow();
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getAllComments(CommentQueryParams params) {
        CommentSpecifications commentSpecifications = new CommentSpecifications(params);
        return commentRepository.findAll(commentSpecifications);
    }

    public Comment createComment(User authenticatedUser, CreateCommentDTO newComment) {
        try {
            Comment comment = Comment.builder()
                    .author(authenticatedUser)
                    .content(newComment.getContent())
                    .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .build();

            return commentRepository.save(comment);
        } catch (NullPointerException e) {
            throw new MissingRequiredAttributeException();
        }
    }

    public void deleteComment(User authenticatedUser, long commentId) {
        User existingUser = commentRepository.findById(commentId).orElseThrow().getAuthor();

        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, existingUser)) {
            throw new AuthorizationException("You are not allowed to delete this comment");
        }

        commentRepository.deleteById(commentId);
    }
}
