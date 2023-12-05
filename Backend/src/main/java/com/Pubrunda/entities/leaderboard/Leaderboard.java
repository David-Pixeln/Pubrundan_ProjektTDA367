package com.Pubrunda.entities.leaderboard;

import com.Pubrunda.entities.leaderboardEntry.LeaderboardEntry;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @NonNull
    @OneToMany
    @JoinColumn(name = "leaderboard_entry_id")
    private List<LeaderboardEntry> leaderboardEntryList;

}
