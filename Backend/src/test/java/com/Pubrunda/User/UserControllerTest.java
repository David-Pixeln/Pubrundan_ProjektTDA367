package com.Pubrunda.User;

import com.Pubrunda.ControllerTest;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected MockMvc mockMvc;

    protected String getBaseUrl() {
        return "http://localhost:" + port + "/api";
    }


    @Autowired
    private UserRepository userRepository;


    @Before
    public final void preloadDB() {
        userRepository.save(new User("test1", "test1", Role.APP_ADMIN));
        userRepository.save(new User("test2", "test2", Role.PUB_ADMIN));
        userRepository.save(new User("test3", "test3", Role.USER));
        userRepository.save(new User("test1", "test1", Role.USER));
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
    }

    @Test
    public void getUsersShouldReturnAllUsers() throws Exception {
        ResultActions response = mockMvc.perform(get(getBaseUrl() + "/users"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].username").value("test1"))
                .andExpect(jsonPath("$[1].username").value("test2"))
                .andExpect(jsonPath("$[2].username").value("test3"))
                .andExpect(jsonPath("$[3].username").value("test1"));

        for (int i = 0; i < 4; i++) {
            response.andExpect(jsonPath("$[" + i + "].id").exists());
            response.andExpect(jsonPath("$[" + i + "].username").exists());
            response.andExpect(jsonPath("$[" + i + "].password").doesNotExist());
            response.andExpect(jsonPath("$[" + i + "].role").exists());
        }
    }

    @Test
    public void getUserByIdShouldReturnOneUser() throws Exception {
        long userId = userRepository.findByUsername("test2").orElseThrow().getId();
        ResultActions response = mockMvc.perform(get(getBaseUrl() + "/users/" + userId));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.username").value("test2"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.role").exists());
    }

    @Test
    public void getUserByIdShouldReturnNotFoundIfUserDoesNotExist() throws Exception {
        ResultActions response = mockMvc.perform(get(getBaseUrl() + "/users/999"));

        response.andExpect(status().isNotFound());
    }

}