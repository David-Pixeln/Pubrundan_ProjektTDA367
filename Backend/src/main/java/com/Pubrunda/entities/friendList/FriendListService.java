package com.Pubrunda.entities.friendList;

import com.Pubrunda.events.CreationEvent;
import com.Pubrunda.entities.friendList.dto.request.FriendListQueryParams;
import com.Pubrunda.entities.friendRequest.FriendRequestAcceptedEvent;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendListService {

    private final FriendListRepository friendListRepository;


    public FriendList getFriendList(long userId) {
        return friendListRepository.findById(userId).orElse(null);
    }

    public FriendList getFriendList(User user) {
        return getFriendList(user.getId());
    }

    public List<FriendList> getAllFriendLists() {
        return friendListRepository.findAll();
    }

    public List<FriendList> getAllFriendLists(FriendListQueryParams params) {
        return friendListRepository.findAll(new FriendListSpecifications(params));
    }

    private FriendList createFriendListForUser(User user) {
        FriendList newFriendList = FriendList.builder()
                .owner(user)
                .build();

        return friendListRepository.save(newFriendList);
    }

    private void deleteFriendListOfUser(User user) {
        deleteFriendList(getFriendList(user));
    }

    private void deleteFriendList(FriendList friendList) {
        friendListRepository.delete(friendList);
    }

    private void removeFriendFromFriendList(FriendList friendList, User friend) {
        friendList.getFriends().remove(friend);
        friendListRepository.save(friendList);
    }

    private void addFriendToFriendList(FriendList friendList, User friend) {
        friendList.getFriends().add(friend);
        friendListRepository.save(friendList);
    }

    @EventListener
    public void handleUserRegistrationEvent(CreationEvent<User> event) {
        createFriendListForUser(event.getCreatedObject());
    }

    @EventListener
    public void handleFriendRequestAcceptedEvent(FriendRequestAcceptedEvent event) {
        addFriendToFriendList(getFriendList(event.getFrom()), event.getTo());
        addFriendToFriendList(getFriendList(event.getTo()), event.getFrom());
    }

}