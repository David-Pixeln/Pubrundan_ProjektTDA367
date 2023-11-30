package com.Pubrunda.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    @NonNull
    private LocalDateTime timestamp;

    @Setter
    @NonNull
    private String sentFrom; //TODO change type String to User

    @Setter
    @NonNull
    private String sentTo; //TODO change type String to User
}
