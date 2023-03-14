package com.example.manipedi.DB.room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.manipedi.DB.room.Schema.Post;

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

    @Insert
    void insert(Post post);

    @Delete
    void delete(Post post);
}
