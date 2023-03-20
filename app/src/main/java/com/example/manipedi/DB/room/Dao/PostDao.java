package com.example.manipedi.DB.room.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.DB.room.Schema.FullPost;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from Post")
    List<Post> getAll();

    @Query("select * from Post where id = :postId")
    Post getPostById(String postId);

    @Query("select * from Post where owner = :ownerName")
    Post getPostsByUser(String ownerName);

    @Query("select * from Post where location = :location")
    Post getPostsByLocation(String location);

    @Query("select * from Post where score = :score")
    Post getPostsByRating(float score);

    @Transaction
    @Query("SELECT * FROM Post")
    List<FullPost> getPostsWithUser();

    @Transaction
    @Query("SELECT * FROM Post WHERE owner=:userId")
    List<Post> getPostsByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Insert
    void insert(Post post);

    @Delete
    void delete(Post post);

    @Update
    void update(Post post);
}
