package com.Pubrunda.models.leaderboardEntry;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class LeaderboardEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    private String contestant; //TODO change from String type to User type

    //TODO add CompletedPubCrawl attribute

    @Setter
    private Integer score;

    private ScoreStrategy scoreStrategy;



    public LeaderboardEntry(ScoreStrategy scoreStrategy) {
        this.scoreStrategy = scoreStrategy;
    }
}
