package com.Pubrunda.Review;

import com.Pubrunda.ControllerTest;
import com.Pubrunda.DTOMapper;
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
            UserDTO postAuthor = DTOMapper.convertToDto(allReviews.get(i).getAuthor(), UserDTO.class);

            ReviewDTO responseReview = responseReviewList.get(i);

            assertThat(responseReview).isEqualTo(review);
            assertThat(responseReview.getAuthor()).isEqualTo(postAuthor);
        }
    }

    @Test
    public void getAllReviewsWithReviewQueryParamForAuthorIdShouldReturnAllReviewsFromAuthor() throws Exception {
        User author = userRepository.findAll().get(1);
        List<Review> existingReviews = reviewRepository.findAll().stream().filter(review -> review.getAuthor().equals(author)).toList();
        List<ReviewDTO> existingReviewDto = DTOMapper.convertToDto(existingReviews, ReviewDTO.class);
        setAuthUser(author);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("authorId", String.valueOf(author.getId()))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<ReviewDTO> responseReviews = getObjectListFromResponse(response, ReviewDTO.class);

        assertThat(responseReviews).isEqualTo(existingReviewDto);
    }

    @Test
    public void getAllReviewsWithReviewQueryParamForAuthorUsernameShouldReturnAllReviewsFromAuthor() throws Exception {
        User author = userRepository.findAll().get(1);
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
    public void getAllReviewWithReviewQueryParamForAfterShouldReturnAllReviewsAfterASetTime() throws Exception {
        LocalDateTime queryTime = LocalDateTime.MIN.withYear(2015);
        List<Review> reviewsAfterQueryTime = reviewService.getAllReviews(ReviewQueryParams.builder().after(queryTime).build());
        List<ReviewDTO> reviewDTO = DTOMapper.convertToDto(reviewsAfterQueryTime, ReviewDTO.class);
        setAuthUser(userRepository.findAll().getFirst());

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("after", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<ReviewDTO> responseReviews = getObjectListFromResponse(response, ReviewDTO.class);

        assertThat(responseReviews).isEqualTo(reviewDTO);
    }

    @Test
    public void getAllReviewsWithReviewQueryParamForBeforeShouldReturnAllReviewsBeforeASetTime() throws Exception {
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
    public void getAllReviewsUnauthorizedShouldReturnUnauthorizedRequest() throws Exception {
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isUnauthorized());
    }

    @Test
    public void getReviewByIdShouldReturnOneReviewWithCorrectId() throws Exception {
        Review firstReview = reviewRepository.findAll().getFirst();
        long postId = firstReview.getId();

        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.author.id").value(firstReview.getAuthor().getId()))
                .andExpect(jsonPath("$.createdAt").value(firstReview.getCreatedAt().toString()))
                .andExpect(jsonPath("$.imagePath").value(firstReview.getMediaPath()));
    }
    /*
     * POST
     */

    @Test
    public void reviewRequestShouldCreateAReview() throws Exception {
        User author = userRepository.findAll().getFirst();
        CreateReviewDTO newReview = new CreateReviewDTO(1, "contentPlaceholder", "mediaPathPlaceholder");

        setAuthUser(author);
        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newReview));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.author.id").value(author.getId()))
                .andExpect(jsonPath("$.rating").exists())
                .andExpect(jsonPath("$.mediaPath").value(newReview.getMediaPath()))
                .andExpect(jsonPath("$.content").value(newReview.getContent()))
                .andExpect(jsonPath("$.createdAt").value(newReview.getContent()));
    }

    @Test
    public void reviewRequestWithMissingRequiredArgumentsShouldReturnBadRequest() throws Exception {
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
    public void reviewRequestUnauthorizedShouldReturnUnauthorizedRequest() throws Exception {
        CreateReviewDTO newReview = new CreateReviewDTO();

        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newReview));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author.id").doesNotExist())
                .andExpect(jsonPath("$.rating").doesNotExist())
                .andExpect(jsonPath("$.mediaPath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist());
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
        long postId = secondPost.getId();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isForbidden());
    }

    @Test
    public void deleteRequestUnauthorizedShouldReturnUnauthorizedRequest() throws Exception {
        Post firstPost = postRepository.findAll().getFirst();
        long postId = firstPost.getId();

        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isUnauthorized());
    }
}