package com.Pubrunda.entities.leaderboard.dto;

import com.Pubrunda.entities.leaderboardEntry.LeaderboardEntry;
import lombok.Data;

import java.util.List;

@Data
public class leaderboardDTO {
    private long id;
    private List<LeaderboardEntry> leaderboardEntryList;
}
