package com.Pubrunda.User;

import com.Pubrunda.auth.dto.request.RegisterUserRequest;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserController;
import com.Pubrunda.entities.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@RequiredArgsConstructor
public class UserControllerTest {

    private static final String API_ENDPOINT = "${api.baseurl}/users";

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private final UserService service;


    @Test
    public void testAddUserShouldReturn400BadRequest() throws Exception {
        User user = new User("test", "test", Role.USER);


    }

}
