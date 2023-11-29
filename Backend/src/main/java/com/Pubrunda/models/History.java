package com.Pubrunda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO CompletedPubCrawls

    //TODO Pubs

}
