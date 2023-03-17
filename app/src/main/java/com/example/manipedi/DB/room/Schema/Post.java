package com.example.manipedi.DB.room.Schema;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.manipedi.DB.room.ManiPediApplication;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String id="";
    public String owner="";
    public String location="";
    public String score = "";
    public String description="";
    public String image = "";
    private Long lastUpdated;

    public static final String LAST_UPDATED = "lastUpdated";
    public static final String LOCAL_LAST_UPDATED = "users_local_last_update";


    public Post(@NonNull String id,
                String owner,
                String location,
                String score,
                String description,
                String image) {
        this.id = id;
        this.owner = owner;
        this.location = location;
        this.score = score;
        this.description = description;
        this.image = image;
    }

    public Post(Map<String, Object> obj) {
        this((String) obj.get("id"),
                (String) obj.get("user"),
                "",
                (String) obj.get("score"),
                (String) obj.get("description"),
                (String) obj.get("image"));
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, Object> toObject() {
        Map<String, Object> obj = new HashMap<>();
        obj.put("id", getId());
        obj.put("user", getOwner());
        obj.put("image", getImage());
        obj.put("description", getDescription());
        obj.put("score", getScore());

        return obj;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = ManiPediApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = ManiPediApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }
}
