package com.example.manipedi.DB.room;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.manipedi.DB.Listener;
import com.example.manipedi.DB.firebase.FirebasePost;
import com.example.manipedi.DB.firebase.UserAuthentication;
import com.example.manipedi.DB.firebase.UserFirebase;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.DB.room.Schema.PostWithUser;
import com.example.manipedi.DB.room.Schema.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DBImplementation {
    private static DBImplementation _instance;

    public final MutableLiveData<LoadingState> usersListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    public final MutableLiveData<LoadingState> postsListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    final public MutableLiveData<LoadingState> userPostsListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    public final MutableLiveData<LoadingState> postsWithUserListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);

    private final MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    private final MutableLiveData<User> profileUser = new MutableLiveData<>();
    private final MutableLiveData<List<PostWithUser>> postsWithUser = new MutableLiveData<>();
    private final MutableLiveData<List<Post>> userPosts = new MutableLiveData<>();

    private final FirebasePost firebasePost = new FirebasePost();
    private final UserFirebase firebaseUser = new UserFirebase();

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

    // Posts
    public void getAllPosts(Listener<List<Post>> callback) {
        executor.execute(()->{
            List<Post> data = localDB.postDao().getAll();
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    public void addPost(Post p, Listener<Void> listener){
        executor.execute(()->{
            localDB.postDao().insertAll(p);
            mainHandler.post(() -> listener.onComplete(null));
        });
    }

    private void insertPostsInRoom(List<Post> posts) {
        for (Post post : posts) {
            executor.execute(() -> {
                localDB.postDao().insertAll(post);
            });
        }
    }


    public void getSpecificPostOfUser(Listener<Post> callback, String id) {
        executor.execute(()->{
            Post data = localDB.postDao().getPostById(id);
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    // Users
    public void getAllUsers(Listener<List<User>> callback) {
        executor.execute(()->{
            List<User> data = localDB.userDao().getAll();
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
    }

    public void addUser(User user, Listener<Void> listener) {
        firebaseUser.addUser(user, (Void) -> {
            executor.execute(() -> {
                localDB.userDao().insert(user);
                refreshAllUsers((v) -> {});
            });
            listener.onComplete(null);
        });
    }

    private void insertUsersInRoom(List<User> users) {
        for (User user : users) {
            executor.execute(() -> {
                localDB.userDao().insertAll(user);
            });
        }
    }

    private void postProfileUser(User user) {
        profileUser.postValue(user);
    }



    // refresh

    public void refreshAllPosts(Listener<Void> listener) {
        postsListLoadingState.postValue(LoadingState.LOADING);

//        Long localLastUpdate = Post.getLocalLastUpdate();
        firebasePost.getAllPostsSince(list -> executor.execute(() -> {
//            Long time = updatePostsInRoom(localLastUpdate, list);
            insertPostsInRoom(list);
//            Post.setLocalLastUpdate(time);
            posts.postValue(localDB.postDao().getAll());
            postsListLoadingState.postValue(LoadingState.NOT_LOADING);
            listener.onComplete(null);
        }));
    }

    public void refreshAllPostsWithUser() {
        postsWithUserListLoadingState.postValue(LoadingState.LOADING);
        refreshUserPosts((value) -> executor.execute(() -> {
            List<PostWithUser> postsList = localDB.postDao().getPostsWithUser();
            postsWithUser.postValue(postsList);
            postsWithUserListLoadingState.postValue(LoadingState.NOT_LOADING);
        }));
    }

    public void refreshUserPosts(Listener<Void> listener) {
        usersListLoadingState.postValue(LoadingState.LOADING);
        refreshAllUsers((v1) -> refreshAllPosts((v2) -> executor.execute(() -> {
            List<Post> userPosts = localDB.postDao().getPostsByUserId(UserAuthentication.getInstance().getUserUid());
            this.userPosts.postValue(userPosts);
            userPostsListLoadingState.postValue(LoadingState.NOT_LOADING);
            listener.onComplete(null);
        })));
    }

    public void refreshAllUsers(Listener<Void> listener) {
        usersListLoadingState.postValue(LoadingState.LOADING);
//        Long localLastUpdate = User.getLocalLastUpdate();
        firebaseUser.getAllUsersSince(list -> executor.execute(() -> {
//            Long time = updateUsersInRoom(localLastUpdate, list);
            insertUsersInRoom(list);
//            User.setLocalLastUpdate(time);
            users.postValue(localDB.userDao().getAll());
            postProfileUser(localDB.userDao().findById(UserAuthentication.getInstance().getUserUid()));
            usersListLoadingState.postValue(LoadingState.NOT_LOADING);
            listener.onComplete(null);
        }));
    }
}
