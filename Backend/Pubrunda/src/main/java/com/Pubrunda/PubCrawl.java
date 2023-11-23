package com.Pubrunda;


import com.Pubrunda.exception.MethodNotImplementedException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@Entity
public class PubCrawl {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    private ArrayList<Pub> pubs;
    private LocalTime earliestOpeningTime;
    private LocalTime latestClosingTime;
    private LocalDateTime lastUpdatedTime;
    private User creator;

    PubCrawl(String name, LocalTime earliestOpeningTime, LocalTime latestClosingTime, User creator) {
        this.name = name;
        this.earliestOpeningTime = earliestOpeningTime;
        this.latestClosingTime = latestClosingTime;
    }

    public String getName(){
        return name;
    }

    public LocalTime getEarliestOpeningTime(){
        LocalTime earliest = pubs.get(0).getOpeningTime();

        for(Pub pub: pubs) {
            if (pub.getOpeningTime() < earliest){
                earliest = pub.getOpeningTime();
            }
        }
        return earliest;
    }

    public LocalTime getLatestClosingTime(){
        LocalTime latest = pubs.get(0).getClosingTime();

        for (Pub pub: pubs){
            LocalTime pubTime = pub.getOpeningTime();
            int result = compareTimesFromNoon(pubTime, latest);
            if (result > 0){
                latest = pub.getClosingTime();
            }
        }
        return name;
    }


    public float getLength(){
        throw new MethodNotImplementedException();
    }



    private static int compareTimesFromNoon(LocalTime time1, LocalTime time2) {
        int minutes1 = time1.getHour() * 60 + time1.getMinute();
        int minutes2 = time2.getHour() * 60 + time2.getMinute();

        return Integer.compare(minutes1, minutes2);
    }

}
