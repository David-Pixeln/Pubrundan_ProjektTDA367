package com.Pubrunda.entities.leaderboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaderboards")
public class LeaderboardController {

    private final LeaderboardRepository repository;

    LeaderboardController(LeaderboardRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{leaderboardId}")
    public Leaderboard getLeaderboardById(@PathVariable long leaderboardId) {
        return repository.findById(leaderboardId).orElseThrow();
    }

    // CREATE
    @PostMapping
    public Leaderboard createLeaderboard(@RequestBody Leaderboard newLeaderboard) {
        return repository.save(newLeaderboard);
    }

    // UPDATE
    @PutMapping("/{leaderboardId}")
    public Leaderboard updateLeaderboard(@RequestBody Leaderboard newLeaderboard, @PathVariable Long leaderboardId) {
        Leaderboard existingLeaderboard = repository.findById(leaderboardId).orElseThrow();
        return repository.save(existingLeaderboard);
    }

    // DELETE
    @DeleteMapping("/{leaderboardId}")
    public ResponseEntity<Leaderboard> deleteUser(@PathVariable Long leaderboardId) {
        Leaderboard existingLeaderboard = repository.findById(leaderboardId).orElseThrow();
        repository.delete(existingLeaderboard);
        return ResponseEntity.ok().build();
    }
}
