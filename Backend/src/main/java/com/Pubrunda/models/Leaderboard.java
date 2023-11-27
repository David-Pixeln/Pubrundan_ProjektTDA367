package com.Pubrunda.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO attribute for list of LeaderboardEntry
    
    public Leaderboard() {}
}
