package com.Pubrunda.leaderboardEntry;

import com.Pubrunda.completedPubCrawl.CompletedPubCrawl;
import com.Pubrunda.user.User;
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
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User contestant;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_pub_crawl_id")
    private CompletedPubCrawl completedPubCrawl;

    @NonNull
    private Integer score;

    public LeaderboardEntry(ScoreStrategy scoreStrategy) {
    }

    public void setScore(ScoreStrategy scoreStrategy) {
        score = scoreStrategy.calculateScore( /* Insert argument of CompletedPubCrawl type */);
    }

}
