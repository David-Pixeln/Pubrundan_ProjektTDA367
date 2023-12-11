package com.Pubrunda.entities.leaderboard.dto;

import com.Pubrunda.entities.leaderboardEntry.LeaderboardEntry;
import lombok.Data;

import java.util.List;

@Data
public class leaderboardQueryParams {
    private List<LeaderboardEntry> leaderboardEntries;
}
