package com.Pubrunda.entities.review;

import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getAll(ReviewQueryParams params) {
        ReviewSpecification reviewSpecification = new ReviewSpecification(params);
        return reviewRepository.findAll(reviewSpecification);
    }

    public Review getReviewById(long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow();
    }

    public Review createReview(Review newReview) {
        return reviewRepository.save(newReview);
    }

    public void deleteReview(long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
