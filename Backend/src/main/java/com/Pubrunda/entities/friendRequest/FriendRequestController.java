package com.Pubrunda.entities.friendRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friendRequests")
public class FriendRequestController {

    private final FriendRequestRepository repository;

    FriendRequestController(FriendRequestRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{friendRequestId}")
    public FriendRequest getFriendRequestById(@PathVariable long friendRequestId) {
        return repository.findById(friendRequestId).orElseThrow();
    }

    // CREATE
    @PostMapping
    public FriendRequest createFriendRequest(@RequestBody FriendRequest newFriendRequest) {
        return repository.save(newFriendRequest);
    }

    // DELETE
    @DeleteMapping("/{friendRequestId}")
    public ResponseEntity<FriendRequest> deleteUser(@PathVariable Long friendRequestId) {
        FriendRequest existingFriendRequest = repository.findById(friendRequestId).orElseThrow();
        repository.delete(existingFriendRequest);
        return ResponseEntity.ok().build();
    }

}
