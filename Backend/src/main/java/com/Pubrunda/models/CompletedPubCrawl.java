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
public class CompletedPubCrawl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO list of completedPubs

    //TODO PubCrawl

    @NonNull
    @Setter
    private LocalDateTime timeStamp;
}
