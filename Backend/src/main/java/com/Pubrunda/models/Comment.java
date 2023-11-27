package com.Pubrunda.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Setter
    private String content;

    //TODO add user/author

    public Comment (String content) {
        this.content = content;
    }

}
