package com.Pubrunda.entities.user;

import com.Pubrunda.entities.user.dto.UserQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<User> findAll(UserQueryParams params) {
        UserSpecifications userSpecification = new UserSpecifications(params);
        return userRepository.findAll(userSpecification);
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

}
