package com.Pubrunda.entities.user;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.user.dto.request.UpdateUserParams;
import com.Pubrunda.entities.user.dto.request.UserQueryParams;
import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // GET
    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable long userId) {
        return DTOMapper.convertToDto(userService.getUser(userId), UserDTO.class);
    }

    @GetMapping
    public List<UserDTO> getAllUsers(UserQueryParams params) {
        // TODO: Add pagination
        // TODO: Add sorting

        return DTOMapper.convertToDto(userService.getAllUsers(params), UserDTO.class);
    }

    // UPDATE
    @PutMapping("/{userId}")
    public UserDTO updateUser(
            @AuthenticationPrincipal User authenticatedUser,
            @PathVariable long userId,
            @RequestBody UpdateUserParams newUserDetails
    ) {
        User updatedUser = userService.updateUser(authenticatedUser, userId, newUserDetails);
        return DTOMapper.convertToDto(updatedUser, UserDTO.class);
    }

    // DELETE
    @DeleteMapping("/{userId}")
    public MessageResponse deleteUser(
            @AuthenticationPrincipal User authenticatedUser,
            @PathVariable long userId
    ) {
        userService.deleteUser(authenticatedUser, userId);
        return new MessageResponse("User deleted successfully");
    }

}
