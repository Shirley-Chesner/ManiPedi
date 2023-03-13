package com.example.manipedi.DB.room;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DBImplementation {
    private static DBImplementation _instance;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    private final AppLocalDbRepository localDB = AppLocalDB.getAppDB();

    private DBImplementation() {}

    public static DBImplementation instance(){
        if (_instance == null) {
            _instance = new DBImplementation();
        }
        return _instance;
    }


    public interface GetAllPostsListener{
        void onComplete(List<Post> data);
    }
    public void getAllPosts(GetAllPostsListener callback) {
        executor.execute(()->{
            List<Post> data = localDB.postDao().getAll();
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    public interface AddPostListener{
        void onComplete();
    }
    public void addPost(Post p, AddPostListener listener){
    executor.execute(()->{
        localDB.postDao().insertAll(p);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        mainHandler.post(()->{
            listener.onComplete();
        });
    });
    }
}
