package com.Pubrunda;

import com.Pubrunda.auth.services.JwtService;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.After;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    protected ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

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
        return post(url).headers(getHeaders()).content(toJsonString(body)).contentType(MediaType.APPLICATION_JSON);
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

    public String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getObjectFromJsonResponse(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }

    public <T> List<T> getObjectListFromJsonResponse(String json, Class<T> clazz) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        return objectMapper.readValue(
                rootNode.traverse(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
    }

}
