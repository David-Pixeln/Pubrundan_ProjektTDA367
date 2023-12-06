package com.Pubrunda.User;

import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserService;
import com.Pubrunda.entities.user.Role;
import com.Pubrunda.entities.user.dto.request.UserQueryParams;
import com.Pubrunda.entities.user.UserSpecifications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.Pubrunda.entities.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser1;
    private User testUser2;

    @BeforeEach
    void setUp() {
        testUser1 = new User("testUser1", "password1", Role.USER);
        testUser2 = new User("testUser2", "password2", Role.USER);

        given(userRepository.findAll()).willReturn(List.of(testUser1, testUser2));

        given(userRepository.findById(testUser1.getId())).willReturn(Optional.ofNullable(testUser1));
    }

    @Test
    @DisplayName("Test find all users")
    public void testFindAllUsers() {
        UserQueryParams queryParams = new UserQueryParams();

        //UserSpecifications spec = new UserSpecifications(new UserQueryParams("asdasd"));

        List<User> result = userService.findAll(queryParams);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).isEqualTo(List.of(testUser1, testUser2));
    }

    /*
    @Test
    @DisplayName("Test get user by ID")
    public void testGetUserById() {
        long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.of(testUser));

        User result = userService.getUserById(userId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(testUser);
    }
    */

    // You can continue with similar tests for other methods like updateUser and deleteUser

    // Add tests for edge cases, exceptions, and any additional business logic

}

