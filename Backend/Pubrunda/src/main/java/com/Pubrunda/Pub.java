package com.Pubrunda;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

}
