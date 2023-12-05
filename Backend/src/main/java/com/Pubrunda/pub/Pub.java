package com.Pubrunda.pub;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Pub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private LocalTime openingTime;

    @NonNull
    private LocalTime closingTime;

    @NonNull
    private LocalDateTime lastUpdatedTime;

}
