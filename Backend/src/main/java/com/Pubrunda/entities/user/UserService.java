package com.Pubrunda.entities.user;

import com.Pubrunda.AuthorizationManager;
import com.Pubrunda.entities.user.dto.request.UpdateUserParams;
import com.Pubrunda.entities.user.dto.request.UserQueryParams;
import com.Pubrunda.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUsers(UserQueryParams params) {
        UserSpecifications userSpecification = new UserSpecifications(params);
        return userRepository.findAll(userSpecification);
    }

    public User updateUser(User authenticatedUser, long userId, UpdateUserParams newUser) {
        return updateUser(authenticatedUser, getUser(userId), newUser);
    }

    public User updateUser(User authenticatedUser, User user, UpdateUserParams newUserDetails) {
        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, user)) {
            throw new AuthorizationException("You are not allowed to update this user");
        }

        return updateUser(user, newUserDetails);
    }

    private User updateUser(User user, UpdateUserParams newUserDetails) {
        if (newUserDetails.getUsername() != null) {
            user.setUsername(newUserDetails.getUsername());
        }

        if (newUserDetails.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(newUserDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(User authenticatedUser, long userId) {
        deleteUser(authenticatedUser, getUser(userId));
    }

    public void deleteUser(User authenticatedUser, User user) {
        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, user)) {
            throw new AuthorizationException("You are not allowed to delete this user");
        }

        deleteUser(user);
    }

    private void deleteUser(User user) {
        userRepository.delete(user);
    }

}
