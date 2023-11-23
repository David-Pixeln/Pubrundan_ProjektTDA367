package com.Pubrunda;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;
import com.Pubrunda.exception.MethodNotImplementedException;

@Entity
public class Pub {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private LocalDateTime lastUpdatedTime;

    Pub() {}

    Pub(String name, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getName(){
        return name;
    }
    public LocalTime getOpeningTime(){
        return openingTime;
    }
    public LocalTime getClosingTime(){
        return closingTime;
    }


}
