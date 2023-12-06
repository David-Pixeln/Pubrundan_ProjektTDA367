package com.Pubrunda.entities.user;

import com.Pubrunda.entities.pub.Pub;
import com.Pubrunda.entities.user.dto.request.UpdateUserParams;
import com.Pubrunda.entities.user.dto.response.UserDTO;
import com.Pubrunda.entities.user.dto.request.UserQueryParams;
import com.Pubrunda.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/users")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;

    private final UserService userService;


    // GET
    @GetMapping
    public List<UserDTO> getAllUsers(UserQueryParams params) {
        // TODO: Add pagination
        // TODO: Add sorting

        return userService.findAll(params).stream().map(this::convertToDto).toList();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return convertToDto(user);
    }


    // UPDATE
    @PutMapping("/{userId}")
    public UserDTO updateUser(@AuthenticationPrincipal User authenticatedUser, @RequestBody UpdateUserParams newUserDetails, @PathVariable long userId) {
        User updatedUser = userService.updateUser(authenticatedUser, newUserDetails, userId);
        return convertToDto(updatedUser);
    }

    // DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal User authenticatedUser, @PathVariable long userId) {
        userService.deleteUser(authenticatedUser, userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    private UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}