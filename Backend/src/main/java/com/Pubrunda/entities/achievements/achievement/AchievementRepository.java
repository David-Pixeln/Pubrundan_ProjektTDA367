package com.Pubrunda.entities.achievements.achievement;

import com.Pubrunda.entities.achievements.achievementset.AchievementSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<AchievementSet, Long> {

}