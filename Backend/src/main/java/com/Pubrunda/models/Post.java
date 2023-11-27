package com.Pubrunda.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@RequiredArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Setter
    @NonNull
    private String author;
    //TODO add user/author

    @Setter
    @NonNull
    private LocalTime lastUpdatedTime;
    @Setter
    @NonNull
    private String imagePath;
    @Setter
    private long numberOfLikes;
    @Setter
    private String content;



}
