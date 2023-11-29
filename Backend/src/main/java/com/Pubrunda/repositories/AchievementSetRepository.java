package com.Pubrunda.repositories;

import com.Pubrunda.models.AchievementSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementSetRepository extends JpaRepository<AchievementSet, Long> {

}
