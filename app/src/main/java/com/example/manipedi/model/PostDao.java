package com.example.manipedi.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from Post")
    List<Post> getAll();

    @Query("select * from Post where id = :postId")
    Post getPostById(String postId);

    @Query("select * from Post where owner = :ownerName")
    Post getPostsByOwner(String ownerName);

    @Query("select * from Post where location = :location")
    Post getPostsByLocation(String location);

    @Query("select * from Post where score = :score")
    Post getPostsByRating(float score);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);
}
