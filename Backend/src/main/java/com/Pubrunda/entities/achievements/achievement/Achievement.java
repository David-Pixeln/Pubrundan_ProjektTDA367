package com.Pubrunda.entities.achievements.achievement;

import com.Pubrunda.entities.achievements.achievementStep.AchievementStep;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Achievement {
    @Setter
    @NonNull
    private boolean completed = false;
    @NonNull
    private AchievementStep steps;

    public float getProgress() {
        if (completed) {
            return 1;
        }
        return steps.getProgress();
    }

    public void updateCompleted() {
        if (getProgress() >= 1) {
            completed = true;
        }
    }
}