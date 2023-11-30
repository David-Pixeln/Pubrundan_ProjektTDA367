package com.Pubrunda.models.leaderboardEntry;

import com.Pubrunda.models.CompletedPubCrawl;
import jakarta.persistence.*;
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

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private CompletedPubCrawl completedPubCrawl;

    @NonNull
    private Integer score;

    public LeaderboardEntry(ScoreStrategy scoreStrategy) {
    }

    public void setScore(ScoreStrategy scoreStrategy) {
        score = scoreStrategy.calculateScore( /* Insert argument of CompletedPubCrawl type */);
    }

}
