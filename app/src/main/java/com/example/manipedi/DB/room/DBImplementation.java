package com.example.manipedi.DB.room;

import com.example.manipedi.DB.room.Dao.PostDao;
import com.example.manipedi.DB.room.Dao.UserDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DBImplementation {
    private static DBImplementation _instance;

    public final Executor executor = Executors.newSingleThreadExecutor();
    public final AppLocalDbRepository localDB = AppLocalDB.getAppDB();

    private DBImplementation() {}

    public static DBImplementation instance(){
        if (_instance == null) {
            _instance = new DBImplementation();
        }
        return _instance;
    }

   public PostDao getPostDao() {
        return localDB.postDao();
   }

    public UserDao getUserDao() {
        return localDB.userDao();
    }
}
