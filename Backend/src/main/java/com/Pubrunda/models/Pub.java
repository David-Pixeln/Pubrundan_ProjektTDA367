package com.Pubrunda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Pub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    @Setter
    private String name;

    @Setter
    private LocalTime openingTime;

    @Setter
    private LocalTime closingTime;

    @Setter
    private LocalDateTime lastUpdatedTime;

    public Pub(String name, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

}
