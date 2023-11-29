package com.Pubrunda.models.leaderboardEntry;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
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

    public void setScore(ScoreStrategy scoreStrategy) {
        score = scoreStrategy.calculateScore( /* Insert argument of CompletedPubCrawl type */);
    }

    public LeaderboardEntry(ScoreStrategy scoreStrategy) {}
}
