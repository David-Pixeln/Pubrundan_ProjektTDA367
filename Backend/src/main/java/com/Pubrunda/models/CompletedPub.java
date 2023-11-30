package com.Pubrunda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter

@RequiredArgsConstructor
@NoArgsConstructor
public class CompletedPub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Setter
    private String completedPubName;

    @NonNull
    @Setter
    private LocalDateTime timeAtCompletedPub;
}
