package com.Pubrunda.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    private LocalDateTime sentTime;

    @Setter
    private String sentFrom; //TODO change type String to User

    @Setter
    private String sentTo; //TODO change type String to User

    public FriendRequest() {}
}
