package com.Pubrunda.Review;

import com.Pubrunda.RepositoryTest;
import com.Pubrunda.entities.review.Review;
import com.Pubrunda.entities.review.ReviewRepository;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ReviewRepositoryTest extends RepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;


    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);

        userRepository.saveAll(List.of(testUser1, testUser2));

        reviewRepository.save(Review.builder()
                .author(testUser1)
                .rating(1)
                .mediaPath("MediaPathPlaceHolder")
                .content("contentPlaceHolder")
                .createdAt(LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40))
                .build());
        reviewRepository.save(Review.builder()
                .author(testUser2)
                .rating(2)
                .mediaPath("MediaPathPlaceHolder")
                .content("contentPlaceHolder")
                .createdAt(LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5))
                .build());
        reviewRepository.save(Review.builder()
                .author(testUser1)
                .rating(3)
                .mediaPath("MediaPathPlaceHolder")
                .content("contentPlaceHolder")
                .createdAt(LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15))
                .build());
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Test
    public void deleteReviewShouldNotDeleteUser() {
        reviewRepository.deleteAll();

        assertThat(reviewRepository.findAll()).isEmpty();
        assertThat(userRepository.findAll()).isNotEmpty();
        assertThat(userRepository.findAll()).hasSize(2);
    }

    @Test
    public void deleteUserShouldRemoveAllUserReviews() {
        userRepository.deleteAll();

        assertThat(userRepository.findAll()).isEmpty();
        assertThat(reviewRepository.findAll()).isEmpty();
    }

}