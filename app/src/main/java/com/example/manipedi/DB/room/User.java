package com.example.manipedi.DB.room;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//import com.example.be_my_guest.MyApplication;
//import com.google.firebase.Timestamp;
//import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date birthDate;
    private Long lastUpdated;
    private String photoUrl;

    public static final String COLLECTION = "Users";
    public static final String ID = "id";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String BIRTH_DATE = "birthDate";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String LOCAL_LAST_UPDATED = "users_local_last_update";
    public static final String PHOTO_URL = "photoUrl";

    public User(@NonNull String id, String firstName, String lastName, String email, String photoUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photoUrl = photoUrl;
    }

//    public User(User user) {
//        this.id = user.getId();
//        this.firstName = user.getFirstName();
//        this.lastName = user.getLastName();
//        this.email = user.getEmail();
//        this.phoneNumber = user.getPhoneNumber();
//        this.birthDate = user.getBirthDate();
//        this.photoUrl = user.getPhotoUrl();
//        this.lastUpdated = user.getLastUpdated();
//    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public static User fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String firstName = (String) json.get(FIRST_NAME);
        String lastName = (String) json.get(LAST_NAME);
        String email = (String) json.get(EMAIL);
        String photoUrl = (String) json.get(PHOTO_URL);

        User user = new User(id, firstName, lastName, email, photoUrl);

        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(FIRST_NAME, getFirstName());
        json.put(LAST_NAME, getLastName());
        json.put(EMAIL, getEmail());
        json.put(PHONE, getPhoneNumber());
        json.put(BIRTH_DATE, getBirthDate());
//        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        json.put(PHOTO_URL, getPhotoUrl());

        return json;
    }

//    public static Long getLocalLastUpdate() {
//        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
//        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
//    }
//
//    public static void setLocalLastUpdate(Long time) {
//        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putLong(LOCAL_LAST_UPDATED,time);
//        editor.commit();
//    }
}
