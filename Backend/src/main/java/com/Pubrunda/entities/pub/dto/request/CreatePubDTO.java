package com.Pubrunda.entities.pub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePubDTO {

    private String name;

    private LocalDateTime openingTime;

    private LocalDateTime closingTime;

    private double longitude;

    private double latitude;
}
