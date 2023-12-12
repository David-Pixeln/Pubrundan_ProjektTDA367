package com.Pubrunda.entities.leaderboardEntry;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaderboardEntries")
public class LeaderboardEntryController {

    private final LeaderboardEntryRepository repository;

    LeaderboardEntryController(LeaderboardEntryRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{leaderboardId}")
    public LeaderboardEntry getLeaderboardById(@PathVariable long leaderboardId) {
        return repository.findById(leaderboardId).orElseThrow();
    }

    // CREATE
    @PostMapping
    public LeaderboardEntry createLeaderboardEntry(@RequestBody LeaderboardEntry newLeaderboardEntry) {
        return repository.save(newLeaderboardEntry);
    }

    // UPDATE
    @PutMapping("/{leaderboardId}")
    public LeaderboardEntry updateLeaderboardEntry(@RequestBody ScoreStrategy scoreStrategy, @PathVariable Long leaderboardEntryId) {
        LeaderboardEntry existingLeaderboardEntry = repository.findById(leaderboardEntryId).orElseThrow();
        existingLeaderboardEntry.setScore(scoreStrategy);
        return repository.save(existingLeaderboardEntry);
    }

    // DELETE
    @DeleteMapping("/{leaderboardId}")
    public ResponseEntity<LeaderboardEntry> deleteLeaderboardEntry(@PathVariable Long leaderboardEntryId) {
        LeaderboardEntry existingLeaderboardEntry = repository.findById(leaderboardEntryId).orElseThrow();
        repository.delete(existingLeaderboardEntry);
        return ResponseEntity.ok().build();
    }
}
