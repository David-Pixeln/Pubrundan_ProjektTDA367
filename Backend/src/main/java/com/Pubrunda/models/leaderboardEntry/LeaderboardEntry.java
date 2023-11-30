package com.Pubrunda.models.leaderboardEntry;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class LeaderboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @NonNull
    private String contestant; // FIXME: change from String type to User type

    // TODO: add CompletedPubCrawl attribute

    @NonNull
    private Integer score;

    public LeaderboardEntry(ScoreStrategy scoreStrategy) {
    }

    public void setScore(ScoreStrategy scoreStrategy) {
        score = scoreStrategy.calculateScore( /* Insert argument of CompletedPubCrawl type */);
    }

}
