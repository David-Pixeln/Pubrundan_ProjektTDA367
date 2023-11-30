package com.Pubrunda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    // TODO: insert user
    @NonNull
    private String user;

    @Setter
    private String mediaPath;

    @NonNull
    private Integer rating;

    @NonNull
    private LocalDateTime createdAt;

}
