package com.Pubrunda.Review;

import com.Pubrunda.ControllerTest;
import com.Pubrunda.DTOMapper;
import com.Pubrunda.JsonObjectMapper;
import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.post.PostService;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.review.DTO.request.CreateReviewDTO;
import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import com.Pubrunda.entities.review.DTO.response.ReviewDTO;
import com.Pubrunda.entities.review.Review;
import com.Pubrunda.entities.review.ReviewRepository;
import com.Pubrunda.entities.review.ReviewService;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.entities.user.dto.response.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerTest extends ControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @Override
    protected String getBaseUrl() {
        return super.getBaseUrl() + "/reviews";
    }

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
        userRepository.deleteAll(); // TODO: Add this for postServiceTest as well
        reviewRepository.deleteAll();
    }

    /*
     * GET
     */

    @Test
    public void getReviewsShouldReturnAllReviewsWithCorrectAttributes() throws Exception {
        List<Review> allReviews = reviewRepository.findAll();
        List<User> allUsers = userRepository.findAll();
        setAuthUser(allUsers.getFirst());

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(3));

        List<ReviewDTO> responseReviewList = getObjectListFromResponse(response, ReviewDTO.class);

        for (int i = 0; i < allReviews.size(); i++) {
            ReviewDTO review = DTOMapper.convertToDto(allReviews.get(i), ReviewDTO.class);
            UserDTO reviewAuthor = DTOMapper.convertToDto(allReviews.get(i).getAuthor(), UserDTO.class);

            ReviewDTO responseReview = responseReviewList.get(i);

            assertThat(responseReview).isEqualTo(review);
            assertThat(responseReview.getAuthor()).isEqualTo(reviewAuthor);
        }
    }

    @Test
    public void getAllReviewsWithQueryParamForAuthorIdShouldReturnAllReviewsFromAuthor() throws Exception {
        User author = userRepository.findAll().getFirst();
        List<Review> existingReviews = reviewRepository.findAll().stream().filter(review -> review.getAuthor().equals(author)).toList();
        List<ReviewDTO> existingReviewsDto = DTOMapper.convertToDto(existingReviews, ReviewDTO.class);
        setAuthUser(author);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("authorId", String.valueOf(author.getId()))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<ReviewDTO> responseReviews = getObjectListFromResponse(response, ReviewDTO.class);

        assertThat(responseReviews).isEqualTo(existingReviewsDto);
    }

    @Test
    public void getAllReviewsWithQueryParamForAuthorUsernameShouldReturnAllReviewsFromAuthor() throws Exception {
        User author = userRepository.findAll().getFirst();
        List<Review> existingReviews = reviewRepository.findAll().stream().filter(review -> review.getAuthor().equals(author)).toList();
        List<ReviewDTO> existingReviewsDto = DTOMapper.convertToDto(existingReviews, ReviewDTO.class);
        setAuthUser(author);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("authorUsername", author.getUsername())
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<ReviewDTO> responseReviews = getObjectListFromResponse(response, ReviewDTO.class);

        assertThat(responseReviews).isEqualTo(existingReviewsDto);
    }

    @Test
    public void getAllReviewWithQueryParamForAfterShouldReturnAllReviewsAfterASetTime() throws Exception {
        LocalDateTime queryTime = LocalDateTime.MIN.withYear(2015);
        List<Review> reviewsAfterQueryTime = reviewService.getAllReviews(ReviewQueryParams.builder().after(queryTime).build());
        List<ReviewDTO> reviewsDTO = DTOMapper.convertToDto(reviewsAfterQueryTime, ReviewDTO.class);
        setAuthUser(userRepository.findAll().getFirst());

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("after", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<ReviewDTO> responseReviews = getObjectListFromResponse(response, ReviewDTO.class);

        assertThat(responseReviews).isEqualTo(reviewsDTO);
    }

    @Test
    public void getAllReviewsWithQueryParamForBeforeShouldReturnAllReviewsBeforeASetTime() throws Exception {
        LocalDateTime queryTime = LocalDateTime.MAX.withYear(2015);
        List<Review> reviewsAfterQueryTime = reviewService.getAllReviews(ReviewQueryParams.builder().before(queryTime).build());
        List<ReviewDTO> reviewsDTO = DTOMapper.convertToDto(reviewsAfterQueryTime, ReviewDTO.class);
        setAuthUser(userRepository.findAll().getFirst());

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("before", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<ReviewDTO> responseReviews = getObjectListFromResponse(response, ReviewDTO.class);

        assertThat(responseReviews).isEqualTo(reviewsDTO);
    }

    @Test
    public void getAllReviewsUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.rating").doesNotExist())
                .andExpect(jsonPath("$.mediaPath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void getReviewByIdShouldReturnOneReviewWithCorrectId() throws Exception {
        Review firstReview = reviewRepository.findAll().getFirst();
        ReviewDTO firstReviewDTO = DTOMapper.convertToDto(firstReview, ReviewDTO.class);

        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + firstReview.getId()));

        ReviewDTO responseReview = getObjectFromResponse(response, ReviewDTO.class);

        assertThat(responseReview).isEqualTo(firstReviewDTO);
    }

    @Test
    public void getReviewByIdShouldReturnNotFoundIfReviewDoesNotExist() throws Exception {
        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + "/999"));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void getReviewByIdUnauthenticatedShouldReturnUnAuthorizedRequest() throws Exception {
        Review firstReview = reviewRepository.findAll().getFirst();
        long reviewId = firstReview.getId();

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + reviewId));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.rating").doesNotExist())
                .andExpect(jsonPath("$.mediaPath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    /*
     * POST
     */

    @Test
    public void postRequestShouldCreateAReview() throws Exception {
        User author = userRepository.findAll().getFirst();
        CreateReviewDTO newReview = new CreateReviewDTO(1, "contentPlaceholder", "mediaPathPlaceholder");

        setAuthUser(author);
        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newReview));

        response.andExpect(status().isCreated());

        ReviewDTO responseReview = getObjectFromResponse(response, ReviewDTO.class);

        Review createdReview = reviewRepository.findAll().getLast();
        ReviewDTO createdReviewDTO = DTOMapper.convertToDto(createdReview, ReviewDTO.class);

        assertThat(responseReview).isEqualTo(createdReviewDTO);
    }

    @Test
    public void postRequestWithMissingRequiredArgumentsShouldReturnBadRequest() throws Exception {
        User author = userRepository.findAll().getFirst();
        CreateReviewDTO newReview = new CreateReviewDTO();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newReview));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author.id").doesNotExist())
                .andExpect(jsonPath("$.rating").doesNotExist())
                .andExpect(jsonPath("$.mediaPath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist());
    }

    @Test
    public void postRequestUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        CreateReviewDTO newReview = new CreateReviewDTO(1, "contentPlaceholder", "mediaPathPlaceholder");

        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newReview));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.rating").doesNotExist())
                .andExpect(jsonPath("$.mediaPath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    /*
     * DELETE
     */

    @Test
    public void deleteRequestShouldDeleteReview() throws Exception {
        User author = userRepository.findAll().getFirst();
        Review firstReview = reviewRepository.findAll().getFirst();
        long reviewId = firstReview.getId();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + reviewId));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void deleteOtherReviewThanYourOwnShouldReturnForbiddenRequest() throws Exception {
        User author = userRepository.findAll().getFirst();
        Review secondReview = reviewRepository.findAll().get(1);
        long reviewId = secondReview.getId();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + reviewId));

        response.andExpect(status().isForbidden());
    }

    @Test
    public void deleteRequestUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        Review firstReview = reviewRepository.findAll().getFirst();
        long reviewId = firstReview.getId();

        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + reviewId));

        response.andExpect(status().isUnauthorized());
    }

}