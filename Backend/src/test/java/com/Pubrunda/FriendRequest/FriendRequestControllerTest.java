package com.Pubrunda.FriendRequest;

import com.Pubrunda.ControllerTest;
import com.Pubrunda.DTOMapper;
import com.Pubrunda.entities.friendRequest.FriendRequest;
import com.Pubrunda.entities.friendRequest.FriendRequestRepository;
import com.Pubrunda.entities.friendRequest.FriendRequestService;
import com.Pubrunda.entities.friendRequest.dto.request.FriendRequestQueryParams;
import com.Pubrunda.entities.friendRequest.dto.response.FriendRequestDTO;
import com.Pubrunda.entities.image.Image;
import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.post.PostService;
import com.Pubrunda.entities.post.dto.request.CreatePostDTO;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.entities.user.dto.response.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FriendRequestControllerTest extends ControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendRequestService friendRequestService;


    @Override
    protected String getBaseUrl() {
        return super.getBaseUrl() + "/friendRequests";
    }

    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);
        User testUser3 = new User("test3", "test3", Role.USER);

        userRepository.saveAll(List.of(testUser1, testUser2, testUser3));

        LocalDateTime dateTime1 = LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5);
        LocalDateTime dateTime3 = LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15);

        friendRequestRepository.save(new FriendRequest(dateTime1, testUser1, testUser2));
        friendRequestRepository.save(new FriendRequest(dateTime2, testUser2, testUser1));
        friendRequestRepository.save(new FriendRequest(dateTime3, testUser2, testUser3));
        friendRequestRepository.save(new FriendRequest(dateTime3, testUser3, testUser2));
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
        friendRequestRepository.deleteAll();
    }

    /*
     * GET
     */

    @Test
    public void getAllFriendRequestsShouldReturnAllFriendRequests() throws Exception {
        List<FriendRequest> allFriendRequests = friendRequestRepository.findAll();
        List<User> allUsers = userRepository.findAll();
        setAuthUser(allUsers.getFirst());

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(3));

        List<FriendRequestDTO> responseFriendRequestList = getObjectListFromResponse(response, FriendRequestDTO.class);

        for (int i = 0; i < allFriendRequests.size(); i++) {
            FriendRequestDTO friendRequest = DTOMapper.convertToDto(allFriendRequests.get(i), FriendRequestDTO.class);
            UserDTO friendRequestFrom = DTOMapper.convertToDto(allFriendRequests.get(i).getFrom(), UserDTO.class);
            UserDTO friendRequestTo = DTOMapper.convertToDto(allFriendRequests.get(i).getTo(), UserDTO.class);

            FriendRequestDTO responseFriendRequest = responseFriendRequestList.get(i);

            assertThat(responseFriendRequest).isEqualTo(friendRequest);
            assertThat(responseFriendRequest.getFrom()).isEqualTo(friendRequestFrom);
            assertThat(responseFriendRequest.getTo()).isEqualTo(friendRequestTo);
        }
    }

    @Test
    public void getAllFriendRequestsWithQueryParamForFromIdShouldReturnAllFriendRequestsSentFromUserWithMatchingId() throws Exception {
        User user = userRepository.findAll().get(1);
        List<FriendRequest> existingFriendRequests = friendRequestRepository.findAll().stream().filter(friendRequest -> friendRequest.getFrom().equals(user)).toList();
        List<FriendRequestDTO> existingFriendRequestsDto = DTOMapper.convertToDto(existingFriendRequests, FriendRequestDTO.class);
        setAuthUser(user);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("fromId", String.valueOf(user.getId()))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<FriendRequestDTO> responseFriendRequests = getObjectListFromResponse(response, FriendRequestDTO.class);

        assertThat(responseFriendRequests).isEqualTo(existingFriendRequestsDto);
    }

    @Test
    public void getAllFriendRequestsWithQueryParamForToIdShouldReturnAllFriendRequestsSentToUserWithMatchingId() throws Exception {
        User user = userRepository.findAll().get(1);
        List<FriendRequest> existingFriendRequests = friendRequestRepository.findAll().stream().filter(friendRequest -> friendRequest.getTo().equals(user)).toList();
        List<FriendRequestDTO> existingFriendRequestsDto = DTOMapper.convertToDto(existingFriendRequests, FriendRequestDTO.class);
        setAuthUser(user);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("toId", String.valueOf(user.getId()))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<FriendRequestDTO> responseFriendRequests = getObjectListFromResponse(response, FriendRequestDTO.class);

        assertThat(responseFriendRequests).isEqualTo(existingFriendRequestsDto);
    }

    @Test
    public void getAllFriendRequestWithQueryParamForFromUsernameShouldReturnAllFriendRequestsSentFromUserWithMatchingUsername() throws Exception {
        User user = userRepository.findAll().get(1);
        List<FriendRequest> existingFriendRequests = friendRequestRepository.findAll().stream().filter(friendRequest -> friendRequest.getFrom().equals(user)).toList();
        List<FriendRequestDTO> existingFriendRequestsDto = DTOMapper.convertToDto(existingFriendRequests, FriendRequestDTO.class);
        setAuthUser(user);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("fromUsername", user.getUsername())
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<FriendRequestDTO> responseFriendRequests = getObjectListFromResponse(response, FriendRequestDTO.class);

        assertThat(responseFriendRequests).isEqualTo(existingFriendRequestsDto);
    }

    @Test
    public void getAllFriendRequestWithQueryParamForToUsernameShouldReturnAllFriendRequestsSentToUserWithMatchingUsername() throws Exception {
        User user = userRepository.findAll().get(1);
        List<FriendRequest> existingFriendRequests = friendRequestRepository.findAll().stream().filter(friendRequest -> friendRequest.getTo().equals(user)).toList();
        List<FriendRequestDTO> existingFriendRequestsDto = DTOMapper.convertToDto(existingFriendRequests, FriendRequestDTO.class);
        setAuthUser(user);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("toUsername", user.getUsername())
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<FriendRequestDTO> responseFriendRequests = getObjectListFromResponse(response, FriendRequestDTO.class);

        assertThat(responseFriendRequests).isEqualTo(existingFriendRequestsDto);
    }

    @Test
    public void getAllFriendRequestWithQueryParamForAfterShouldReturnAllFriendRequestsAfterASetTime() throws Exception {
        User authenticatedUser = userRepository.findAll().getFirst();
        LocalDateTime queryTime = LocalDateTime.MIN.withYear(2015);
        List<FriendRequest> friendRequestsAfterQueryTime = friendRequestService.getAllFriendRequests(authenticatedUser, FriendRequestQueryParams.builder().after(queryTime).build());
        List<FriendRequestDTO> friendRequestsDTO = DTOMapper.convertToDto(friendRequestsAfterQueryTime, FriendRequestDTO.class);
        setAuthUser(authenticatedUser);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("after", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(3));

        List<FriendRequestDTO> responseFriendRequests = getObjectListFromResponse(response, FriendRequestDTO.class);

        assertThat(responseFriendRequests).isEqualTo(friendRequestsDTO);
    }

    @Test
    public void getAllFriendRequestsWithQueryParamForBeforeShouldReturnAllFriendRequestsBeforeASetTime() throws Exception {
        User authenticatedUser = userRepository.findAll().getFirst();
        LocalDateTime queryTime = LocalDateTime.MAX.withYear(2015);
        List<FriendRequest> friendRequestsAfterQueryTime = friendRequestService.getAllFriendRequests(authenticatedUser, FriendRequestQueryParams.builder().before(queryTime).build());
        List<FriendRequestDTO> friendRequestsDTO = DTOMapper.convertToDto(friendRequestsAfterQueryTime, FriendRequestDTO.class);
        setAuthUser(authenticatedUser);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("before", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<FriendRequestDTO> responseFriendRequests = getObjectListFromResponse(response, FriendRequestDTO.class);

        assertThat(responseFriendRequests).isEqualTo(friendRequestsDTO);
    }

    @Test
    public void getAllFriendRequestsUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.from").doesNotExist())
                .andExpect(jsonPath("$.to").doesNotExist());
    }

    @Test
    public void getFriendRequestByIdShouldReturnOneFriendRequestWithCorrectId() throws Exception {
        FriendRequest firstFriendRequest = friendRequestRepository.findAll().getFirst();
        FriendRequestDTO firstFriendRequestDTO = DTOMapper.convertToDto(firstFriendRequest, FriendRequestDTO.class);

        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + firstFriendRequest.getId()));

        response.andExpect(status().isOk());

        FriendRequestDTO responseFriendRequest = getObjectFromResponse(response, FriendRequestDTO.class);

        assertThat(responseFriendRequest).isEqualTo(firstFriendRequestDTO);
    }

    @Test
    public void getFriendRequestByIdShouldReturnNotFoundIfFriendRequestDoesNotExist() throws Exception {
        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + "/999"));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.from").doesNotExist())
                .andExpect(jsonPath("$.to").doesNotExist());
    }

    @Test
    public void getFriendRequestByIdUnauthenticatedShouldReturnUnAuthorizedRequest() throws Exception {
        FriendRequest firstFriendRequest = friendRequestRepository.findAll().getFirst();
        long friendRequestId = firstFriendRequest.getId();

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + friendRequestId));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.from").doesNotExist())
                .andExpect(jsonPath("$.to").doesNotExist());
    }

    /*
     * POST
     */

    @Test
    public void postRequestShouldCreateAPost() throws Exception {
        User author = userRepository.findAll().getFirst();

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "image".getBytes()
        );

        CreatePostDTO newPost = new CreatePostDTO(List.of(image), "testContent");

        setAuthUser(author);
        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newPost));

        response.andExpect(status().isCreated());

        PostDTO responsePost = getObjectFromResponse(response, PostDTO.class);

        Post createdPost = postRepository.findAll().getLast();
        PostDTO createdPostDTO = DTOMapper.convertToDto(createdPost, PostDTO.class);

        assertThat(responsePost).isEqualTo(createdPostDTO);
    }

    @Test
    public void postRequestWithMissingRequiredArgumentsShouldReturnBadRequest() throws Exception {
        User author = userRepository.findAll().getFirst();
        CreatePostDTO newPost = new CreatePostDTO();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newPost));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void postRequestUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        CreatePostDTO newPost = new CreatePostDTO();

        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newPost));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    /*
     * DELETE
     */

    @Test
    public void deleteRequestShouldDeletePost() throws Exception {
        User author = userRepository.findAll().getFirst();
        Post firstPost = postRepository.findAll().getFirst();
        long postId = firstPost.getId();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void deleteOtherPostThanYourOwnShouldReturnForbiddenRequest() throws Exception {
        User author = userRepository.findAll().getFirst();
        Post secondPost = postRepository.findAll().get(1);
        long postId = secondPost.getId();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isForbidden());
    }

    @Test
    public void deleteRequestUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        Post firstPost = postRepository.findAll().getFirst();
        long postId = firstPost.getId();

        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isUnauthorized());
    }


}
