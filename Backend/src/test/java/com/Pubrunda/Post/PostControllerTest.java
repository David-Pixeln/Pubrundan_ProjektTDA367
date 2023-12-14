package com.Pubrunda.Post;

import com.Pubrunda.ControllerTest;
import com.Pubrunda.DTOMapper;
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
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest extends ControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;


    @Override
    protected String getBaseUrl() {
        return super.getBaseUrl() + "/posts";
    }

    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);

        userRepository.saveAll(List.of(testUser1, testUser2));

        LocalDateTime dateTime1 = LocalDateTime.of(2010, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.AUGUST, 3, 23, 10, 5);
        LocalDateTime dateTime3 = LocalDateTime.of(2020, Month.DECEMBER, 10, 5, 25, 15);
        postRepository.save(new Post(testUser1, dateTime1, "imagePlaceholder"));
        postRepository.save(new Post(testUser2, dateTime2, "imagePlaceholder"));
        postRepository.save(new Post(testUser2, dateTime3, "imagePlaceholder"));
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll(); // TODO: Add this for postServiceTest as well
        postRepository.deleteAll();
    }

    /*
     * GET
     */

    @Test
    public void getPostsShouldReturnAllPostsWithCorrectAttributes() throws Exception {
        List<Post> allPosts = postRepository.findAll();
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

    @Test
    public void getAllPostsWithPostQueryParamForIdShouldReturnPostWithCorrectId() throws Exception {
        Post existingPost = postRepository.findAll().getFirst();
        PostDTO existingPostDTO = DTOMapper.convertToDto(existingPost, PostDTO.class);
        setAuthUser(userRepository.findAll().getFirst());

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("id", String.valueOf(existingPost.getId()))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(1));

        PostDTO responsePost = getObjectListFromResponse(response, PostDTO.class).getFirst();

        assertThat(responsePost).isEqualTo(existingPostDTO);
    }

    @Test
    public void getAllPostsWithPostQueryParamForAuthorIdShouldReturnAllPostsFromAuthor() throws Exception {
        User author = userRepository.findAll().get(1);
        List<Post> existingPosts = postRepository.findAll().stream().filter(post -> post.getAuthor().equals(author)).toList();
        List<PostDTO> existingPostsDto = DTOMapper.convertToDto(existingPosts, PostDTO.class);
        setAuthUser(author);

        ResultActions response = mockMvc.perform(
                getRequest(getBaseUrl()).param("authorId", String.valueOf(author.getId()))
        );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2));

        List<PostDTO> responsePosts = getObjectListFromResponse(response, PostDTO.class);

        assertThat(responsePosts).isEqualTo(existingPostsDto);
    }

    @Test
    public void getAllPostsWithPostQueryParamForAuthorUsernameShouldReturnAllPostsFromAuthor() throws Exception {
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
    public void getAllPostsUnauthorizedShouldReturnUnauthorizedRequest() throws Exception {
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl()));

        response.andExpect(status().isUnauthorized());
    }

    @Test
    public void getPostByIdShouldReturnOnePostWithCorrectId() throws Exception {
        Post firstPost = postRepository.findAll().getFirst();
        long postId = firstPost.getId();

        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.author.id").value(firstPost.getAuthor().getId()))
                .andExpect(jsonPath("$.createdAt").value(firstPost.getCreatedAt().toString()))
                .andExpect(jsonPath("$.imagePath").value(firstPost.getImagePath()));
    }

    @Test
    public void getPostByIdShouldReturnNotFoundIfPostDoesNotExist() throws Exception {
        setAuthUser(userRepository.findAll().getFirst());
        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + "/999"));

        response.andExpect(status().isNotFound());
    }

    @Test
    public void getPostByIdUnauthorizedShouldReturnUnAuthorizedRequest() throws Exception {
        Post firstPost = postRepository.findAll().getFirst();
        long postId = firstPost.getId();

        ResultActions response = mockMvc.perform(getRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author.id").doesNotExist())
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

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.author.id").value(author.getId()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.imagePath").value(newPost.getImagePath()))
                .andExpect(jsonPath("$.content").value(newPost.getContent()));
    }

    @Test
    public void postRequestWithMissingRequiredArgumentsShouldReturnBadRequest() throws Exception {
        User author = userRepository.findAll().getFirst();
        CreatePostDTO newPost = new CreatePostDTO();

        setAuthUser(author);
        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newPost));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author.id").doesNotExist())
                .andExpect(jsonPath("$.createdAt").doesNotExist())
                .andExpect(jsonPath("$.imagePath").doesNotExist())
                .andExpect(jsonPath("$.content").doesNotExist());
    }

    @Test
    public void postRequestUnauthorizedShouldReturnUnauthorizedRequest() throws Exception {
        CreatePostDTO newPost = new CreatePostDTO();

        ResultActions response = mockMvc.perform(postRequest(getBaseUrl(), newPost));

        response.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.author.id").doesNotExist())
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
    public void deleteRequestUnauthorizedShouldReturnUnauthorizedRequest() throws Exception {
        Post firstPost = postRepository.findAll().getFirst();
        long postId = firstPost.getId();

        ResultActions response = mockMvc.perform(deleteRequest(getBaseUrl() + '/' + postId));

        response.andExpect(status().isUnauthorized());
    }

}