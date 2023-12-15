package com.Pubrunda.FriendRequest;

import com.Pubrunda.ControllerTest;
import com.Pubrunda.DTOMapper;
import com.Pubrunda.entities.friendRequest.FriendRequest;
import com.Pubrunda.entities.friendRequest.FriendRequestRepository;
import com.Pubrunda.entities.friendRequest.FriendRequestService;
import com.Pubrunda.entities.friendRequest.dto.response.FriendRequestDTO;
import com.Pubrunda.entities.post.Post;
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
    protected String getBaseUrl() {return super.getBaseUrl() + "/friendRequests";}

    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);
        User testUser3 = new User("test3", "test3", Role.USER);

        userRepository.saveAll(List.of(testUser1, testUser2, testUser3));

        LocalDateTime dateTime1 = LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5);
        LocalDateTime dateTime3 = LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15);

        friendRequestRepository.save(FriendRequest.builder()
                .createdAt(dateTime1)
                .to(testUser1)
                .from(testUser2)
                .build());
        friendRequestRepository.save(FriendRequest.builder()
                .createdAt(dateTime2)
                .to(testUser2)
                .from(testUser1)
                .build());
        friendRequestRepository.save(FriendRequest.builder()
                .createdAt(dateTime3)
                .to(testUser2)
                .from(testUser3)
                .build());
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
        friendRequestRepository.deleteAll();
    }

    /*
     * GET
     */

    /*
    @Test
    public void getFriendRequestShouldReturnAllFriendRequest() throws Exception {
        List<FriendRequest> allFriendRequests = friendRequestRepository.findAll();
        List<User> allUsers = userRepository.findAll();
        setAuthUser(allUsers.getFirst());

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(3));

        List<PostDTO> responsePostList = getObjectListFromResponse(response, PostDTO.class);

        for (int i = 0; i < allPosts.size(); i++) {
            PostDTO post = DTOMapper.convertToDto(allPosts.get(i), PostDTO.class);
            UserDTO postAuthor = DTOMapper.convertToDto(allPosts.get(i).getAuthor(), UserDTO.class);

            PostDTO responsePost = responsePostList.get(i);

            assertThat(responsePost).isEqualTo(post);
            assertThat(responsePost.getAuthor()).isEqualTo(postAuthor);
        }
    }
    */

    @Test
    public void getAllFriendRequestsWithQueryParamForToShouldReturnAllFriendRequestToUser() throws Exception {
        User user = userRepository.findAll().get(1);
        List<FriendRequest> existingFriendRequests = friendRequestRepository.findAll().stream().filter(friendRequest -> friendRequest.getTo().equals(user)).toList();
        List<FriendRequestDTO> existingFriendRequestsDto = DTOMapper.convertToDto(existingFriendRequests, FriendRequestDTO.class);
        setAuthUser(user);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("to", String.valueOf(user.getId()))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<FriendRequestDTO> responseFriendRequests = getObjectListFromResponse(response, FriendRequestDTO.class);

        assertThat(responseFriendRequests).isEqualTo(existingFriendRequestsDto);
    }

    @Test
    public void getAllFriendRequestsWithQueryParamForAuthorUsernameShouldReturnAllPostsFromAuthor() throws Exception {
        User author = userRepository.findAll().get(1);
        List<Post> existingPosts = postRepository.findAll().stream().filter(post -> post.getAuthor().equals(author)).toList();
        List<PostDTO> existingPostsDto = DTOMapper.convertToDto(existingPosts, PostDTO.class);
        setAuthUser(author);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("authorUsername", author.getUsername())
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<PostDTO> responsePosts = getObjectListFromResponse(response, PostDTO.class);

        assertThat(responsePosts).isEqualTo(existingPostsDto);
    }

    @Test
    public void getAllPostsWithPostQueryParamForAfterShouldReturnAllPostsAfterASetTime() throws Exception {
        LocalDateTime queryTime = LocalDateTime.MIN.withYear(2015);
        List<Post> postsAfterQueryTime = postService.getAllPosts(PostQueryParams.builder().after(queryTime).build());
        List<PostDTO> postsDTO = DTOMapper.convertToDto(postsAfterQueryTime, PostDTO.class);
        setAuthUser(userRepository.findAll().getFirst());

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("after", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<PostDTO> responsePosts = getObjectListFromResponse(response, PostDTO.class);

        assertThat(responsePosts).isEqualTo(postsDTO);
    }

    @Test
    public void getAllPostsWithPostQueryParamForBeforeShouldReturnAllPostsBeforeASetTime() throws Exception {
        LocalDateTime queryTime = LocalDateTime.MAX.withYear(2015);
        List<Post> postsAfterQueryTime = postService.getAllPosts(PostQueryParams.builder().before(queryTime).build());
        List<PostDTO> postsDTO = DTOMapper.convertToDto(postsAfterQueryTime, PostDTO.class);
        setAuthUser(userRepository.findAll().getFirst());

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("before", String.valueOf(queryTime))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<PostDTO> responsePosts = getObjectListFromResponse(response, PostDTO.class);

        assertThat(responsePosts).isEqualTo(postsDTO);
    }

    @Test
    public void getAllPostsUnauthenticatedShouldReturnUnauthorizedRequest() throws Exception {
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void getPostByIdShouldReturnOnePostWithCorrectId() throws Exception {
        Post firstPost = postRepository.findAll().getFirst();
        PostDTO firstPostDTO = DTOMapper.convertToDto(firstPost, PostDTO.class);

        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + firstPost.getId()));

        response.andExpect(status().isOk());

        PostDTO responsePost = getObjectFromResponse(response, PostDTO.class);

        assertThat(responsePost).isEqualTo(firstPostDTO);
    }

    @Test
    public void getPostByIdShouldReturnNotFoundIfPostDoesNotExist() throws Exception {
        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + "/999"));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void getPostByIdUnauthenticatedShouldReturnUnAuthorizedRequest() throws Exception {
        Post firstPost = postRepository.findAll().getFirst();
        long postId = firstPost.getId();

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    /*
     * POST
     */

    @Test
    public void postRequestShouldCreateAPost() throws Exception {
        User author = userRepository.findAll().getFirst();
        CreatePostDTO newPost = new CreatePostDTO("imagePath", "testContent");

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
