package com.example.manipedi.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DBImplementation {
    private static DBImplementation _instance;

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    private AppLocalDbRepository localDB = AppLocalDB.getAppDB();

    private DBImplementation() {}

    public static DBImplementation instance(){
        if (_instance == null) {
            _instance = new DBImplementation();
        }
        return _instance;
    }

//    public void getAllPosts
}
