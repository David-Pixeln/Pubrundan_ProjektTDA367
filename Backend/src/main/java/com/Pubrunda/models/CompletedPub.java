package com.Pubrunda.models;

import jakarta.persistence.*;
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
    @OneToOne
    @JoinColumn(name = "pub_id")
    private Pub pub;

    @NonNull
    @Setter
    private LocalDateTime timeAtCompletedPub;
}
