package com.Pubrunda;

import com.Pubrunda.auth.services.JwtService;
import com.Pubrunda.entities.user.User;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private User authUser;


    @After
    public final void clearAuthUser() {
        authUser = null;
    }

    protected String getBaseUrl() {
        return "http://localhost:" + port + "/api";
    }

    protected void setAuthUser(User user) {
        authUser = user;
    }

    protected MockHttpServletRequestBuilder getRequest(String url) {
        return get(url).headers(getHeaders());
    }

    protected MockHttpServletRequestBuilder postRequest(String url, Object body) {
        //return post(url).headers(getHeaders()).content(body);
        return null;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        if (authUser != null) {
            headers.setBearerAuth(getAuthToken(authUser));
        }

        return headers;
    }

    private String getAuthToken(User user) {
        return jwtService.generateToken(user);
    }

}
