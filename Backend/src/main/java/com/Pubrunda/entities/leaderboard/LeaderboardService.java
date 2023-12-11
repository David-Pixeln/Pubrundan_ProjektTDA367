package com.Pubrunda.entities.leaderboard;

import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.history.History;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.UserRepository;
import com.Pubrunda.entities.user.UserSpecifications;
import com.Pubrunda.entities.user.dto.request.UserQueryParams;
import com.Pubrunda.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    public Leaderboard getLeaderboardById(long leaderboardId) {
        return leaderboardRepository.findById(leaderboardId).orElseThrow();
    }

    public Leaderboard createLeaderboard(Leaderboard newLeaderboard) {
        return leaderboardRepository.save(newLeaderboard);
    }

    public void deleteLeaderboard(long leaderboardId) {
        leaderboardRepository.deleteById(leaderboardId);
    }

    public Leaderboard updateLeaderboard(long leaderboardId, Leaderboard newLeaderboard) {
        Leaderboard existingLeaderboard = leaderboardRepository.getById(leaderboardId);
        existingLeaderboard.setLeaderboardEntryList(newLeaderboard.getLeaderboardEntryList());
        return leaderboardRepository.save(existingLeaderboard);
    }

}
