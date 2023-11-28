package com.Pubrunda.models;

import jakarta.persistence.*;

import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String username;

    @NonNull
    private String passwordHash;

    @NonNull
    private String role;

}