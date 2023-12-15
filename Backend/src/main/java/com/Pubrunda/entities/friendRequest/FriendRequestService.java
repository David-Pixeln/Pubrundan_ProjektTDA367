package com.Pubrunda.entities.friendRequest;

import com.Pubrunda.AuthorizationManager;
import com.Pubrunda.DTOMapper;
import com.Pubrunda.entities.friendRequest.dto.request.CreateFriendRequestDTO;
import com.Pubrunda.entities.friendRequest.dto.request.FriendRequestQueryParams;
import com.Pubrunda.entities.friendRequest.dto.response.FriendRequestDTO;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserService;
import com.Pubrunda.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final UserService userService;

    private final FriendRequestRepository friendRequestRepository;

    private final ApplicationEventPublisher eventPublisher;


    public FriendRequest getFriendRequestById(User authenticatedUser, long friendRequestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).orElseThrow();

        if (!isPartOfFriendRequest(authenticatedUser, friendRequest)) {
            throw new NoSuchElementException(); // Don't leak information about the existence of the friend request
        }

        return friendRequest;
    }

    public List<FriendRequest> getAllFriendRequests(User authenticatedUser) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAll();
        return filterFriendRequests(authenticatedUser, friendRequests);
    }

    public List<FriendRequest> getAllFriendRequests(User authenticatedUser, FriendRequestQueryParams queryParams) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAll(new FriendRequestSpecifications(queryParams));
        return filterFriendRequests(authenticatedUser, friendRequests);
    }

    public FriendRequest createFriendRequest(User authenticatedUser, CreateFriendRequestDTO friendRequest) {
        User toUser = userService.getUserById(friendRequest.getTo().getId());
        return createFriendRequest(authenticatedUser, toUser);
    }

    public FriendRequest createFriendRequest(User authenticatedUser, User toUser) {
        FriendRequest newFriendRequest = FriendRequest.builder()
                .from(authenticatedUser)
                .to(toUser)
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        return friendRequestRepository.save(newFriendRequest);
    }

    public void deleteFriendRequest(User authenticatedUser, long friendRequestId) {
        deleteFriendRequest(authenticatedUser, getFriendRequestById(authenticatedUser, friendRequestId));
    }

    public void deleteFriendRequest(User authenticatedUser, FriendRequest friendRequest) {
        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, friendRequest.getFrom())) {
            throw new AuthorizationException("You are not authorized to delete this friend request");
        }

        deleteFriendRequest(friendRequest);
    }

    private void deleteFriendRequest(FriendRequest friendRequest) {
        friendRequestRepository.delete(friendRequest);
    }

    public void acceptFriendRequest(User authenticatedUser, long friendRequestId) {
        acceptFriendRequest(authenticatedUser, getFriendRequestById(authenticatedUser, friendRequestId));
    }

    public void acceptFriendRequest(User authenticatedUser, FriendRequest friendRequest) {
        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, friendRequest.getTo())) {
            throw new AuthorizationException("You are not authorized to accept this friend request");
        }

        eventPublisher.publishEvent(new FriendRequestAcceptedEvent(this, friendRequest.getFrom(), friendRequest.getTo()));

        deleteFriendRequest(friendRequest);
    }

    private List<FriendRequest> filterFriendRequests(User authenticatedUser, List<FriendRequest> friendRequests) {
        return friendRequests.stream()
                .filter(friendRequest -> isPartOfFriendRequest(authenticatedUser, friendRequest))
                .toList();
    }

    private boolean isPartOfFriendRequest(User user, FriendRequest friendRequest) {
        return user.equals(friendRequest.getFrom()) || user.equals(friendRequest.getTo());
    }

}
