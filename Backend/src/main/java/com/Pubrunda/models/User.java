/*
package com.Pubrunda.models;

import com.Pubrunda.models.PubCrawl;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.awt.Image;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


@Entity
public class User {
    
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String username;
    private String description;
    private Image profileImage;
    private ArrayList<PubCrawl> finishedPubCrawls;
    private ArrayList<PubCrawl> favouritePubCrawls;

    public User() {}

    public User(String name, String username, String description, Image profileImage) {
        this.name = name;
        this.username = username;
        this.description = description;
        this.profileImage = profileImage;
        this.finishedPubCrawls = new ArrayList<PubCrawl>();
        this.favouritePubCrawls = new ArrayList<PubCrawl>();
    }

    // write gettters and setters for these
    public String getName(){
        return name;
    }
    public String getUsername(){
        return username;
    }
    public String getDescription(){
        return description;
    }
    public Image getProfileImage(){
        return profileImage;
    }
    public ArrayList<PubCrawl> getFinishedPubCrawls(){
        return finishedPubCrawls;
    }
    public ArrayList<PubCrawl> getFavouritePubCrawls(){
        return favouritePubCrawls;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setProfileImage(Image profileImage){
        this.profileImage = profileImage;
    }
    
    public void addPubCrawlToFavourites(PubCrawl pubCrawl){
        favouritePubCrawls.add(pubCrawl);
    }
    public void removePubCrawlFromFavourites(PubCrawl pubCrawl){
        favouritePubCrawls.remove(pubCrawl);
    }

}
*/