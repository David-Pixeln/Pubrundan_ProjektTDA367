package com.Pubrunda.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class AchievementSet {

    //TODO list of Achievements

    @Setter
    private Boolean complete;

    public Boolean isComplete(){
        if (complete) {
            return true;
        } else {
            //TODO go through list and check if all are complete, if one is not, return false;
            complete = true;
            return true;
        }
    }

    public double getCompletionPercentage(){
        //TODO iterate through list and get percentage of completed
        return 1.1;
    }



}
