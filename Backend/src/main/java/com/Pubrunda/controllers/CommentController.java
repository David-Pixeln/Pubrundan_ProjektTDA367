package com.Pubrunda.controllers;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.models.Comment;
import com.Pubrunda.models.Pub;
import com.Pubrunda.repositories.CommentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentRepository repository;

    CommentController(CommentRepository repository) {
        this.repository = repository;
    }
    // READ
    @GetMapping("/{commentId}")
    public Comment getCommentById(@PathVariable long commentId) {
        return repository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(commentId));
    }
    // CREATE
    @PostMapping
    public Comment createComment(@RequestBody Comment newComment) {
        return repository.save(newComment);
    }
    // DELETE
    @DeleteMapping("/{pubId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long commentId) {
        Comment existingComment = repository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(commentId));
        repository.delete(existingComment);
        return ResponseEntity.ok().build();
    }

    // TODO get function for user
}
