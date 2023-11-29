package com.Pubrunda.models.leaderboardEntry;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
public class LeaderboardEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Setter
    private String contestant; //TODO change from String type to User type

    //TODO add CompletedPubCrawl attribute

    @NonNull
    private Integer score;

    private void setScore(ScoreStrategy scoreStrategy) {
        score = scoreStrategy.calculateScore();
    }

    public LeaderboardEntry(ScoreStrategy scoreStrategy) {}
}
