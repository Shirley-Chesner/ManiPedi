package com.example.manipedi.DB.room.Schema;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String id="";
    public String owner="";
    public String location="";
    public String score = "";
    public String description="";
    public String avatarURL = "";


    public Post(@NonNull String id,
                String owner,
                String location,
                String score,
                String description,
                String avatarURL
                /*ArrayList<String> pictures*/) {
        this.id = id;
        this.owner = owner;
        this.location = location;
        this.score = score;
        this.description = description;
        this.avatarURL = avatarURL;
//        this.pictures = pictures;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

//    public ArrayList<String> getPictures() {
//        return pictures;
//    }
//
//    public void setPictures(ArrayList<String> pictures) {
//        this.pictures = pictures;
//    }
}
