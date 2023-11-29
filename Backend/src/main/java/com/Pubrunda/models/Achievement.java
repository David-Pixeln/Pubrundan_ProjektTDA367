package com.Pubrunda.models;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Achievement {
    @Setter
    @NonNull
    private Boolean completed = false;
    @NonNull
    private AchievementStep steps;

    public float getProgress() {
        if (completed) {return 1;}
        float progress = steps.getProgress();
        if (progress >= 1) {completed = true;}
        return progress;
    }
}
