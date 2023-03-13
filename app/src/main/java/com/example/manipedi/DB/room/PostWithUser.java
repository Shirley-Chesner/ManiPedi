package com.example.manipedi.DB.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;

public class PostWithUser implements Serializable {
    @Embedded
    public Post post;
    @Relation(
            parentColumn = "userId",
            entityColumn = "id")
    public User user;
}