package com.Pubrunda.entities.leaderboard;

import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.baseurl}/leaderboards")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    // READ
    @GetMapping("/{leaderboardId}")
    public Leaderboard getLeaderboardById(@PathVariable long leaderboardId) {
        return leaderboardService.getLeaderboardById(leaderboardId);
    }

    // CREATE
    @PostMapping
    public Leaderboard createLeaderboard(@RequestBody Leaderboard newLeaderboard) {
        return leaderboardService.createLeaderboard(newLeaderboard);
    }

    // UPDATE
    @PutMapping("/{leaderboardId}")
    public Leaderboard updateLeaderboard(@RequestBody Leaderboard newLeaderboard, @PathVariable Long leaderboardId) {
        Leaderboard existingLeaderboard = leaderboardService.getLeaderboardById(leaderboardId);
        return leaderboardService.updateLeaderboard(leaderboardId, existingLeaderboard);
    }

    // DELETE
    @DeleteMapping("/{leaderboardId}")
    public MessageResponse deleteUser(@PathVariable Long leaderboardId) {
        leaderboardService.deleteLeaderboard(leaderboardId);
        return new MessageResponse("Leaderboard was deleted successfully");
    }
}
