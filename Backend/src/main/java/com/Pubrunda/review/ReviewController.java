package com.Pubrunda.review;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.repositories.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository repository;

    ReviewController(ReviewRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{reviewId}")
    public Review getReviewById(@PathVariable long reviewId) {
        return repository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(reviewId));
    }

    // CREATE
    @PostMapping
    public Review createReview(@RequestBody Review newReview) {
        return repository.save(newReview);
    }

    // DELETE
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long reviewId) {
        Review existingReview = repository.findById(reviewId).orElseThrow(() -> new ResourceNotFoundException(reviewId));
        repository.delete(existingReview);
        return ResponseEntity.ok().build();
    }

}
