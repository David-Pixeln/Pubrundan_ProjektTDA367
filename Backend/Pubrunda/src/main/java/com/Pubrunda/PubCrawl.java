package com.Pubrunda;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class PubCrawl {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private LocalTime earliestOpeningTime;
    private LocalTime latestClosingTime;
    private LocalDateTime lastUpdatedTime;

    PubCrawl() {}

    PubCrawl(String name, LocalTime earliestOpeningTime, LocalTime latestClosingTime) {
        this.name = name;
        this.earliestOpeningTime = earliestOpeningTime;
        this.latestClosingTime = latestClosingTime;
    }

}
