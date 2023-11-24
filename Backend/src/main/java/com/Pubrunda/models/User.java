package com.Pubrunda.models;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Setter
    private String profileImagePath;


    public User() {}

    public User(String name, @NonNull String username, String description, String profileImagePath) {
        this.name = name;
        this.username = username;
        this.description = description;
        this.profileImagePath = profileImagePath;
    }

    public BufferedImage getProfileImage() throws IOException {
        return ImageIO.read(new File(profileImagePath));
    }

}