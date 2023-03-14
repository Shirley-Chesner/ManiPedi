package com.example.manipedi.DB.room;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.DB.room.Schema.User;

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

    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }


    public interface GetAllPostsListener{
        void onComplete(List<Post> data);
    }

    public interface  GetSpecificPostListener{
        void onComplete(Post data);
    }


    public void getAllPosts(GetAllPostsListener callback) {
        executor.execute(()->{
            List<Post> data = localDB.postDao().getAll();
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    public void getSpecificPostOfUser(GetSpecificPostListener callback, String id) {
        executor.execute(()->{
            Post data = localDB.postDao().getPostById(id);
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    public void getAllUsers(GetAllUsersListener callback) {
        executor.execute(()->{
            List<User> data = localDB.userDao().getAll();
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
            mainHandler.post(listener::onComplete);
        });
    }
}
