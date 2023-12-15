package com.Pubrunda.entities.friendRequest;

import com.Pubrunda.entities.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FriendRequestAcceptedEvent extends ApplicationEvent {

    private final User from;
    private final User to;

    public FriendRequestAcceptedEvent(Object source, User from, User to) {
        super(source);
        this.from = from;
        this.to = to;
    }

}
