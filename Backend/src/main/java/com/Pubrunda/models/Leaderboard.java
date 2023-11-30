package com.Pubrunda.models;

import com.Pubrunda.models.leaderboardEntry.LeaderboardEntry;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leaderboard_entry_id")
    private List<LeaderboardEntry> leaderboardEntryList;
}
