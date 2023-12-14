package com.Pubrunda.entities.image;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private String path;

}
