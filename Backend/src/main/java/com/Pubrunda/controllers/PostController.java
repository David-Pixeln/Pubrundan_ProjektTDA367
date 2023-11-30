package com.Pubrunda.controllers;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.models.Post;
import com.Pubrunda.repositories.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostRepository repository;

    PostController(PostRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable long postId) {
        return repository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));
    }

    // CREATE
    @PostMapping()
    public Post createPost(@RequestBody Post newPost) {
        return repository.save(newPost);
    }

    // DELETE
    @DeleteMapping("/{postId}")
    public ResponseEntity<Post> deleteUser(@PathVariable Long postId) {
        Post existingPost = repository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));
        repository.delete(existingPost);
        return ResponseEntity.ok().build();
    }

    // TODO get user function

    // TODO get comments (all) function
}
