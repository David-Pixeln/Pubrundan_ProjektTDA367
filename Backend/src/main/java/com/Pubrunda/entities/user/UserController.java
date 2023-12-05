package com.Pubrunda.entities.user;

import com.Pubrunda.entities.user.dto.UserDTO;
import com.Pubrunda.entities.user.dto.UserQueryParams;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/users")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;

    private final UserService userService;


    @GetMapping
    public List<UserDTO> getAllUsers(UserQueryParams params) {
        // TODO: Add filtering
        // TODO: Add pagination
        // TODO: Add sorting

        return userService.findAll(params).stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return modelMapper.map(user, UserDTO.class);
    }

}
