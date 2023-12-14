package com.Pubrunda.entities.review;

import com.Pubrunda.AuthorizationManager;
import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.exception.AuthorizationException;
import com.Pubrunda.exception.MissingRequiredAttributeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getAllReviews(ReviewQueryParams params) {
        ReviewSpecification reviewSpecification = new ReviewSpecification(params);
        return reviewRepository.findAll(reviewSpecification);
    }

    public Review getReviewById(long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow();
    }

    public Review createReview(User authenticatedUser, Review newReview) {
        try {
            Review review = Review.builder()
                    .author(authenticatedUser)
                    .mediaPath(newReview.getMediaPath())
                    .rating(newReview.getRating())
                    .createdAt(LocalDateTime.now())
                    .build();

            return reviewRepository.save(review);
        } catch (NullPointerException e) {
            throw new MissingRequiredAttributeException();
        }    }

    public void deleteReview(long reviewId) {
        User existingUser = reviewRepository.findById(reviewId).orElseThrow().getAuthor();

        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, existingUser)) {
            throw new AuthorizationException("You are not allowed to delete this post");
        }

        postRepository.deleteById(postId);
    }
    }

}
