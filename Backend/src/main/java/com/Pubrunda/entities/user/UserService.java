package com.Pubrunda.entities.user;

import com.Pubrunda.entities.user.dto.request.UserQueryParams;
import com.Pubrunda.entities.user.dto.request.UpdateUserParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public List<User> findAll(UserQueryParams params) {
        UserSpecifications userSpecification = new UserSpecifications(params);
        return userRepository.findAll(userSpecification);
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User updateUser(User authenticatedUser, UpdateUserParams newUser, long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow();

        if (hasAuthorityOfUser(authenticatedUser, existingUser)) {
            throw new RuntimeException("You are not allowed to update this user");
        }

        existingUser.setUsername(newUser.getUsername());
        existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        return userRepository.save(existingUser);
    }

    public void deleteUser(User authenticatedUser, long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow();

        if (hasAuthorityOfUser(authenticatedUser, existingUser)) {
            throw new RuntimeException("You are not allowed to delete this user");
        }

        userRepository.delete(existingUser);
    }

    private boolean hasAuthorityOfUser(User authenticatedUser, User user) {
        return authenticatedUser.getId() == user.getId();
    }

}
