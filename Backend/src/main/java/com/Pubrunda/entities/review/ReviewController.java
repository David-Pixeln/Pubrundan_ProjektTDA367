package com.Pubrunda.entities.review;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.review.DTO.request.CreateReviewDTO;
import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import com.Pubrunda.entities.review.DTO.response.ReviewDTO;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // READ
    @GetMapping("/{reviewId}")
    public ReviewDTO getReviewById(@PathVariable long reviewId) {
        return DTOMapper.convertToDto(reviewService.getReviewById(reviewId), ReviewDTO.class);
    }

    // List
    @GetMapping
    public List<ReviewDTO> getAllReviews(ReviewQueryParams params) {
        return DTOMapper.convertToDto(reviewService.getAllReviews(params), ReviewDTO.class);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO createReview(@AuthenticationPrincipal User authenticatedUser, @RequestBody CreateReviewDTO newReview) {
        return DTOMapper.convertToDto(reviewService.createReview(authenticatedUser, newReview), ReviewDTO.class);
    }

    // DELETE
    @DeleteMapping("/{reviewId}")
    public MessageResponse deleteReview(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long reviewId) {
        reviewService.deleteReview(authenticatedUser, reviewId);
        return new MessageResponse("Review deleted successfully");
    }

}