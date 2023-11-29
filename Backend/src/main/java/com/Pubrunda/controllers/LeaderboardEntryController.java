package com.Pubrunda.controllers;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.models.leaderboardEntry.LeaderboardEntry;
import com.Pubrunda.repositories.LeaderboardEntryRepository;
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
    @GetMapping("/{pubId}")
    public LeaderboardEntry getLeaderboardById(@PathVariable long leaderboardId) {
        return repository.findById(leaderboardId).orElseThrow(() -> new ResourceNotFoundException(leaderboardId));
    }

    // CREATE
    @PostMapping
    public LeaderboardEntry createLeaderboardEntry(@RequestBody LeaderboardEntry newLeaderboardEntry) {
        return repository.save(newLeaderboardEntry);
    }

    // UPDATE
    @PutMapping("/{pubId}")
    public LeaderboardEntry updateLeaderboardEntry(@RequestBody LeaderboardEntry newLeaderboardEntry, @PathVariable Long leaderboardEntryId) {
        LeaderboardEntry existingLeaderboardEntry = repository.findById(leaderboardEntryId).orElseThrow(() -> new ResourceNotFoundException(leaderboardEntryId));
        existingLeaderboardEntry.setScore(newLeaderboardEntry.getScore());
        return repository.save(existingLeaderboardEntry);
    }

    // DELETE
    @DeleteMapping("/{pubId}")
    public ResponseEntity<LeaderboardEntry> deleteUser(@PathVariable Long leaderboardEntryId) {
        LeaderboardEntry existingLeaderboardEntry = repository.findById(leaderboardEntryId).orElseThrow(() -> new ResourceNotFoundException(leaderboardEntryId));
        repository.delete(existingLeaderboardEntry);
        return ResponseEntity.ok().build();
    }
}
