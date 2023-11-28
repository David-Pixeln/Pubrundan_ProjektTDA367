package com.Pubrunda.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public abstract class AchievementStep {

    @Setter
    private Boolean completed;

}
