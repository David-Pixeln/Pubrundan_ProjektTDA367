package com.Pubrunda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @Setter
    @NonNull
    private String author; // FIXME: add user/author

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    private String imagePath;

    private int numberOfLikes; // FIXME: Should be database table?

    private String content;

}