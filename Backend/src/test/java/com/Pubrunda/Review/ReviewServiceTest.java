package com.Pubrunda.Review;

import com.Pubrunda.ServiceTest;
import com.Pubrunda.entities.review.DTO.request.CreateReviewDTO;
import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import com.Pubrunda.entities.review.Review;
import com.Pubrunda.entities.review.ReviewRepository;
import com.Pubrunda.entities.review.ReviewService;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.exception.AuthorizationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReviewServiceTest extends ServiceTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewService reviewService;


    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);

        userRepository.saveAll(List.of(testUser1, testUser2));

        LocalDateTime dateTime1 = LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5);
        LocalDateTime dateTime3 = LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15);

        reviewRepository.save(Review.builder()
                .author(testUser1)
                .mediaPath("MediaPathPlaceHolder")
                .rating(1)
                .content("contentPlaceHolder")
                .createdAt(dateTime1)
                .build());
        reviewRepository.save(Review.builder()
                .author(testUser2)
                .mediaPath("MediaPathPlaceHolder")
                .rating(2)
                .content("contentPlaceHolder")
                .createdAt(dateTime2)
                .build());
        reviewRepository.save(Review.builder()
                .author(testUser1)
                .mediaPath("MediaPathPlaceHolder")
                .rating(3)
                .content("contentPlaceHolder")
                .createdAt(dateTime3)
                .build());
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    /*
     * Get
     */

    @Test
    public void getAllReviewsShouldReturnThreeReviewsWithCorrectId() {
        List<Review> allReviews = reviewRepository.findAll();

        List<Review> reviews = reviewService.getAllReviews();

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(3);
        assertThat(reviews).isEqualTo(allReviews);
    }

    @Test
    public void getAllReviewsWithQueryParameterForAuthorIdShouldReturnTwoReviewsFromAuthor() {
        User author = userRepository.findAll().getFirst();
        List<Review> authorReviews = getReviewFromAuthor(author);

        ReviewQueryParams reviewQueryParamForAuthor = ReviewQueryParams.builder().authorId(author.getId()).build();
        List<Review> reviews = reviewService.getAllReviews(reviewQueryParamForAuthor);

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(2);
        assertThat(reviews).isEqualTo(authorReviews);
    }

    @Test
    public void getAllReviewsWithQueryParameterForBeforeShouldReturnAllReviewsBeforeTime() {
        Review firstReview = reviewRepository.findAll().getFirst();
        Review secondReview = reviewRepository.findAll().get(1);

        ReviewQueryParams reviewQueryParamForBefore = ReviewQueryParams.builder().before(LocalDateTime.MAX.withYear(2015)).build();
        List<Review> reviews = reviewService.getAllReviews(reviewQueryParamForBefore);

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(2);
        assertThat(reviews.getFirst()).isEqualTo(firstReview);
        assertThat(reviews.getLast()).isEqualTo(secondReview);
    }

    @Test
    public void getAllReviewsWithQueryParameterForAfterShouldReturnAllReviewsAfterTime() {
        Review secondReview = reviewRepository.findAll().get(1);
        Review thirdReview = reviewRepository.findAll().get(2);

        ReviewQueryParams reviewQueryParamForAfter = ReviewQueryParams.builder().after(LocalDateTime.MIN.withYear(2015)).build();
        List<Review> reviews = reviewService.getAllReviews(reviewQueryParamForAfter);

        assertThat(reviews).isNotEmpty();
        assertThat(reviews).hasSize(2);
        assertThat(reviews.getFirst()).isEqualTo(secondReview);
        assertThat(reviews.getLast()).isEqualTo(thirdReview);
    }

    /*
     * Create
     */

    @Test
    public void createReviewShouldAddReviewToDatabase() {
        int postCountBefore = reviewRepository.findAll().size();
        User author = userRepository.findAll().getFirst();
        CreateReviewDTO newReview = new CreateReviewDTO(4, "mediaPathPlaceholder", "contentPlaceholder");

        Review review = reviewService.createReview(author, newReview);

        List<Review> allReviews = reviewRepository.findAll();

        assertThat(allReviews).isNotEmpty();
        assertThat(allReviews).hasSize(postCountBefore + 1);
        assertThat(review.getId()).isEqualTo(allReviews.getLast().getId());
    }

    @Test(expected = AuthorizationException.class)
    public void nonAuthorizedUserShouldNotBeAbleToDeleteOtherUsersReviews() {
        User authorizedUser = userRepository.findAll().getFirst();
        Review reviewByAuthorizedUser = getReviewFromAuthor(authorizedUser).getFirst();

        User nonAuthorizedUser = userRepository.findAll().get(1);

        reviewService.deleteReview(nonAuthorizedUser, reviewByAuthorizedUser.getId());
    }

    /*
     * Delete
     */

    @Test
    public void deleteReviewShouldRemoveReviewFromDatabase() {
        User user = userRepository.findAll().getFirst();
        Review existingReview = getReviewFromAuthor(user).getFirst();

        long existingReviewId = existingReview.getId();
        int reviewCountBefore = reviewRepository.findAll().size();

        reviewService.deleteReview(user, existingReviewId);

        List<Review> allReviews = reviewRepository.findAll();

        assertThat(allReviews).isNotEmpty();
        assertThat(allReviews).hasSize(reviewCountBefore - 1);
        assertThat(allReviews.stream().allMatch(review -> review.getId() != existingReviewId)).isTrue();
    }

    private List<Review> getReviewFromAuthor(User author) {
        return reviewRepository.findAll().stream().filter(review -> review.getAuthor().equals(author)).toList();
    }

}