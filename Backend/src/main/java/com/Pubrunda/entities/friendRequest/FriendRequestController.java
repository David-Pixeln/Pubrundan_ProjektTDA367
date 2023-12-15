package com.Pubrunda.entities.friendRequest;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.friendRequest.dto.request.CreateFriendRequestDTO;
import com.Pubrunda.entities.friendRequest.dto.request.FriendRequestQueryParams;
import com.Pubrunda.entities.friendRequest.dto.response.FriendRequestDTO;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendRequests")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    // READ
    @GetMapping("/{friendRequestId}")
    public FriendRequestDTO getFriendRequestById(@AuthenticationPrincipal User authenticatedUser,
                                                 @PathVariable long friendRequestId) {
        return DTOMapper.convertToDto(friendRequestService.getFriendRequestById(authenticatedUser, friendRequestId), FriendRequestDTO.class);
    }

    @GetMapping("/{friendRequestId}/accept")
    public MessageResponse acceptFriendRequest(@AuthenticationPrincipal User authenticatedUser,
                                                               @PathVariable long friendRequestId) {
        friendRequestService.acceptFriendRequest(authenticatedUser, friendRequestId);
        return new MessageResponse("Friend request accepted successfully");
    }

    @GetMapping
    public List<FriendRequestDTO> getFriendRequests(@AuthenticationPrincipal User authenticatedUser,
                                                    FriendRequestQueryParams queryParams) {
        return DTOMapper.convertToDto(friendRequestService.getAllFriendRequests(authenticatedUser, queryParams), FriendRequestDTO.class);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FriendRequestDTO createFriendRequest(@AuthenticationPrincipal User authenticatedUser,
                                                @RequestBody CreateFriendRequestDTO newFriendRequest) {
        return DTOMapper.convertToDto(friendRequestService.createFriendRequest(authenticatedUser, newFriendRequest), FriendRequestDTO.class);
    }

    // DELETE
    @DeleteMapping("/{friendRequestId}")
    public MessageResponse deleteUser(@AuthenticationPrincipal User authenticatedUser,
                                      @PathVariable long friendRequestId) {
        friendRequestService.deleteFriendRequest(authenticatedUser, friendRequestId);

        return new MessageResponse("Friend request deleted successfully");
    }

}
