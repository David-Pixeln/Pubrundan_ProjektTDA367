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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO insert user
    @NonNull
    @Setter
    private String user;

    @Setter
    private String mediaPath;

    @NonNull
    @Setter
    private Integer rating;

    @NonNull
    @Setter
    private LocalDateTime publishTime;
}
