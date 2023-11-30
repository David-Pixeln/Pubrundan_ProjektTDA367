package com.Pubrunda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
//@RequiredArgsConstructor
@NoArgsConstructor
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO attribute for list of LeaderboardEntry
}
