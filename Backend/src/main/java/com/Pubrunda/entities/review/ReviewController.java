package com.Pubrunda.entities.review;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import com.Pubrunda.entities.review.DTO.response.ReviewDTO;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
<<<<<<< Updated upstream
=======
import org.springframework.security.core.annotation.AuthenticationPrincipal;
>>>>>>> Stashed changes
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

    // List
    @GetMapping
    public List<ReviewDTO> getAllReviews(ReviewQueryParams params) {
        return reviewService.getAllReviews(params).stream().map(review -> modelMapper.map(review, ReviewDTO.class)).toList();
    }

    // CREATE
    @PostMapping
    public Review createReview(@AuthenticationPrincipal User authenticatedUser, @RequestBody ReviewDTO newReview) {
        //return reviewService.createReview(newReview);
        return DTOMapper.convertToDto(reviewService.createReview(authenticatedUser, newReview), ReviewDTO.class);
    }

    // DELETE
    @DeleteMapping("/{reviewId}")
    public MessageResponse deleteReview(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long reviewId) {
        reviewService.deleteReview(authenticatedUser, reviewId);
        return new MessageResponse("Review deleted successfully");
    }
<<<<<<< Updated upstream

    // List
    @GetMapping("/{reviewId}")
    public List<ReviewDTO> getAllReviews(ReviewQueryParams params) {
        return reviewService.getAll(params).stream().map(review -> modelMapper.map(review, ReviewDTO.class)).toList();
    }

=======
>>>>>>> Stashed changes
}
