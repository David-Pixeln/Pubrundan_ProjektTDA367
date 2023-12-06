package com.Pubrunda.entities.achievements.achievementset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementSetRepository extends JpaRepository<AchievementSet, Long> {

}