package com.Pubrunda.User;

import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserTest {

        @Autowired
        private UserRepository userRepository;

        @Test
        void testFindByName() {
            // Given
            User user = new User();
            user.setUsername("John Doe");
            userRepository.save(user);

            // When
            //User foundUser = userRepository.findByName("John Doe");

            // Then
            //assertThat(foundUser).isNotNull();
            //assertThat(foundUser.getName()).isEqualTo("John Doe");
        }
}


