package com.Pubrunda.entities.review;

import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import com.Pubrunda.entities.review.DTO.response.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ModelMapper modelMapper;
    private final ReviewService reviewService;

    // READ
    @GetMapping("/{reviewId}")
    public Review getReviewById(@PathVariable long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    // CREATE
    @PostMapping
    public Review createReview(@RequestBody Review newReview) {
        return reviewService.createReview(newReview);
    }

    // DELETE
    @DeleteMapping("/{reviewId}")
    public MessageResponse deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new MessageResponse("Review deleted successfully");
    }

    // List
    @GetMapping("/{reviewId}")
    public List<ReviewDTO> getAllReviews(ReviewQueryParams params) {
        return reviewService.getAll(params).stream().map(review -> modelMapper.map(review, ReviewDTO.class)).toList();
    }
}
