package com.Pubrunda.entities.user;

import com.Pubrunda.entities.user.dto.UserQueryParams;
import com.Pubrunda.entities.user.specifications.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<User> findAll(UserQueryParams params) {
        UserSpecification userSpecification = new UserSpecification(params);
        return userRepository.findAll(userSpecification);
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

}
