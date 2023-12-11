package com.Pubrunda.entities.leaderboard;

import com.Pubrunda.entities.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long>, JpaSpecificationExecutor<History> {
}
