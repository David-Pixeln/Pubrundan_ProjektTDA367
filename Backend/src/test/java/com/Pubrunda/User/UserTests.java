package com.Pubrunda.User;


import com.Pubrunda.models.Profile;
import com.Pubrunda.models.User;
import com.Pubrunda.repositories.ProfileRepository;
import com.Pubrunda.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void saveUserOperation() {
        User user = new User("test", "pwd", "testRole");
        profileRepository.save(new Profile(user));
    }

    @Test
    void changeUserOperation() {
        /*
        Optional<User> optionalUser = userRepository.findByUsername("test");


        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername("test1");
            user.setPasswordHash("test1");
            user.setRole("test1");

            Profile profile = user.getProfile();
            profile.setName("test1");
            profile.setDescription("test1");

            userRepository.save(user);
        }
        else {
            throw new RuntimeException("User not found");
        }
        */
    }

    @Test
    void deleteUserOperation() {
        Optional<User> optionalUser = userRepository.findByUsername("test1");

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

}
