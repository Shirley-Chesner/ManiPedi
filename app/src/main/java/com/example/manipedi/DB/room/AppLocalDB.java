package com.example.manipedi.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.manipedi.ManiPediApplication;

@Database(entities = {Post.class}, version = 101, exportSchema = false)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();
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
