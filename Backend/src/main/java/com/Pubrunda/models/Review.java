package com.Pubrunda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO insert user
    @Setter
    private String user;

    @Setter
    private String mediaPath;

    @Setter
    private Integer rating;

    @Setter
    private LocalDateTime publishTime;

    public Review () {}
    
}
