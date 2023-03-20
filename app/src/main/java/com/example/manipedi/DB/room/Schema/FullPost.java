package com.example.manipedi.DB.room.Schema;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;

public class FullPost implements Serializable {
    @Embedded
    public Post post;
    @Relation(parentColumn = "owner", entityColumn = "id")
    public User user;
}