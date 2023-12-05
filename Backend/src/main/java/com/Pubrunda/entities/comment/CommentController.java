package com.Pubrunda.entities.comment;

import com.Pubrunda.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long commentId) {
        Comment existingComment = repository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(commentId));
        repository.delete(existingComment);
        return ResponseEntity.ok().build();
    }

    // TODO: get function for user
}
