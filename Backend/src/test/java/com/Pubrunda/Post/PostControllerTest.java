package com.Pubrunda.Post;

import com.Pubrunda.ControllerTest;
import com.Pubrunda.entities.post.Post;
import com.Pubrunda.entities.post.PostRepository;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest extends ControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    @Override
    protected String getBaseUrl() {
        return super.getBaseUrl() + "/posts";
    }

    @Before
    public final void preloadDB() {
        User testUser1 = new User("test1", "test1", Role.USER);
        User testUser2 = new User("test2", "test2", Role.USER);

        LocalDateTime dateTime1 = LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40);
        LocalDateTime dateTime2 = LocalDateTime.of(2015, Month.JUNE, 29, 19, 30, 40);

        postRepository.save(new Post(testUser1, dateTime1, "imagePlaceholder"));
        postRepository.save(new Post(testUser2, dateTime2, "imagePlaceholder"));
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll(); // TODO: Add this for postServiceTest as well
        postRepository.deleteAll();
    }

    @Test
    public void getPostsShouldReturnAllPostsWithCorrectAttributes() throws Exception {
        ResultActions response = mockMvc.perform(get(getBaseUrl()));

        System.out.println(response.andReturn().getResponse().getContentAsString());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(postRepository.findAll().get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(postRepository.findAll().get(1).getId()));

        for (int i = 0; i < 2; i++) {
            response.andExpect(jsonPath("$[" + i + "].id").exists());
            response.andExpect(jsonPath("$[" + i + "].author").exists());
            response.andExpect(jsonPath("$[" + i + "].createdAt").exists());
            response.andExpect(jsonPath("$[" + i + "].imagePath").exists());
        }
    }




    /*
    @Test
    public void getUserByIdShouldReturnOneUser() throws Exception {
        long userId = userRepository.findByUsername("test2").orElseThrow().getId();
        ResultActions response = mockMvc.perform(get(getBaseUrl() + '/' + userId));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value("test2"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.role").exists());
    }

    @Test
    public void getUserByIdShouldReturnNotFoundIfUserDoesNotExist() throws Exception {
        ResultActions response = mockMvc.perform(get(getBaseUrl() + "/999"));

        response.andExpect(status().isNotFound());
    }
    */

}
