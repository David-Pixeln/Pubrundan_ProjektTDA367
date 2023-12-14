package com.Pubrunda;

import com.Pubrunda.auth.services.JwtService;
import com.Pubrunda.entities.user.User;
import lombok.Setter;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private JwtService jwtService;

    @Setter
    private User authUser;

    @Test
    void contextLoads() {}

    @After
    public final void clearAuthUser() {
        authUser = null;
    }

    protected String getBaseUrl() {
        return "http://localhost:" + port + "/api";
    }

    protected MockHttpServletRequestBuilder getRequest(String url) {
        return request(get(url));
    }

    protected MockHttpServletRequestBuilder postRequest(String url, Object body) {
        return request(post(url), body);
    }

    protected MockHttpServletRequestBuilder putRequest(String url, Object body) {
        return request(put(url), body);
    }

    protected MockHttpServletRequestBuilder deleteRequest(String url) {
        return request(delete(url));
    }

    private MockHttpServletRequestBuilder request(MockHttpServletRequestBuilder method) {
        return method.headers(getHeaders());
    }

    private MockHttpServletRequestBuilder request(MockHttpServletRequestBuilder method, Object body) {
        return request(method)
                .content(JsonObjectMapper.toJsonString(body))
                .contentType(MediaType.APPLICATION_JSON);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        if (authUser != null) {
            headers.setBearerAuth(getAuthToken(authUser));
        }

        return headers;
    }

    protected String getResponseContent(ResultActions response) throws UnsupportedEncodingException {
        return response.andReturn().getResponse().getContentAsString();
    }

    protected <T> T getObjectFromResponse(ResultActions response, Class<T> clazz) throws IOException {
        return JsonObjectMapper.getObjectFromJson(getResponseContent(response), clazz);
    }

    protected <T> List<T> getObjectListFromResponse(ResultActions response, Class<T> clazz) throws IOException {
        return JsonObjectMapper.getObjectListFromJson(getResponseContent(response), clazz);
    }

    private String getAuthToken(User user) {
        return jwtService.generateToken(user);
    }

}
