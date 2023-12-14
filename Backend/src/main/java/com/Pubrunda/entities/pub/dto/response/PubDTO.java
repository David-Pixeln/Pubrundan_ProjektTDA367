package com.Pubrunda.entities.pub.dto.response;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PubDTO {

    private long id;
    private UserDTO owner;
    private String name;
    private double longitude;
    private double latitude;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private LocalDateTime lastUpdatedTime;
}
