package com.Pubrunda.Review;

import com.Pubrunda.ServiceTest;
import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.review.Review;
import com.Pubrunda.entities.review.ReviewRepository;
import com.Pubrunda.entities.review.ReviewService;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
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

        int i = 4;
        Integer integer = Integer.valueOf(i);

        LocalDateTime dateTime1 = LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5);
        LocalDateTime dateTime3 = LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15);
        reviewRepository.save(new Review(testUser1, Integer.valueOf(4), dateTime1));
        reviewRepository.save(new Review(testUser2, Integer.valueOf(3), dateTime2));
        reviewRepository.save(new Review(testUser2, Integer.valueOf(5), dateTime3));

        int i = 4;
        Integer integer = Integer.valueOf(i);
        Review testReview = new Review(testUser1, Integer.valueOf(1), dateTime1);
        Review testReview2 = Review.builder().

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
    public void getAllPostsShouldReturnThreePostsWithCorrectId() {
        List<Review> allReviews = reviewRepository.findAll();

        List<Review> reviews = reviewService.getAllReviews();

        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(3);

        for (int i = 0; i < 3; i++) {
            assertThat(posts.get(i).getId()).isEqualTo(allPosts.get(i).getId());
        }
    }

    /*
     * Create
     */




    /*
     * Delete
     */














}

