package com.Pubrunda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

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
