package com.Pubrunda.User;

import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.entities.user.UserService;
import com.Pubrunda.entities.user.dto.request.UserQueryParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Before
    public final void preloadDB() {
        userRepository.save(new User("test1", "test1", Role.APP_ADMIN));
        userRepository.save(new User("test2", "test2", Role.PUB_ADMIN));
        userRepository.save(new User("test3", "test3", Role.USER));
    }

    @After
    public final void cleanDB() {
        userRepository.deleteAll();
    }

    @Test
    public void testGetAllUsersShouldReturnThreeUsers() {
        List<User> users = userService.findAll(new UserQueryParams());

        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(3);
        assertThat(users.get(0).getUsername()).isEqualTo("test1");
        assertThat(users.get(1).getUsername()).isEqualTo("test2");
        assertThat(users.get(2).getUsername()).isEqualTo("test3");
    }

    @Test
    public void testGetUserByIdShouldReturnOneUser() {
        long userId = userRepository.findAll().get(0).getId();

        User user = userService.getUserById(userId);

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("test1");
    }

    @Test
    public void testGetUserByUsernameShouldReturnTest1User() {
        List<User> users = userService.findAll(new UserQueryParams("test1"));

        assertThat(users).isNotNull();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("test1");
    }

}
