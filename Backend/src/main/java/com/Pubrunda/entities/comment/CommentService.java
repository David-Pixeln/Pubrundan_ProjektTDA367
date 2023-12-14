package com.Pubrunda.entities.comment;

import com.Pubrunda.entities.comment.dto.request.CommentQueryParams;
import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.post.PostSpecifications;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        CommentSpecifications = new CommentSpecifications(params);
        return commentRepository.findAll(commentSpecifications);
    }
}
