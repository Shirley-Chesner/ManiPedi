package com.example.manipedi.DB.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.manipedi.DB.room.Dao.PostDao;
import com.example.manipedi.DB.room.Dao.UserDao;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.DB.room.Schema.User;

@Database(entities = {Post.class, User.class}, version = 103, exportSchema = false)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract UserDao userDao();
}

public class AppLocalDB {
    static public AppLocalDbRepository getAppDB() {
        return Room.databaseBuilder(ManiPediApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDB(){}
}
