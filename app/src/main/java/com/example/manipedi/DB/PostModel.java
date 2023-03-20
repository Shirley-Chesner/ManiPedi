package com.example.manipedi.DB;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.manipedi.DB.firebase.PostFirebaseModel;
import com.example.manipedi.DB.room.DBImplementation;
import com.example.manipedi.DB.room.Schema.FullPost;
import com.example.manipedi.DB.room.Schema.Post;

import java.util.List;

public class PostModel {
    private static final PostModel instance = new PostModel();
    DBImplementation db = DBImplementation.instance();

    private final PostFirebaseModel postFirebaseModel = new PostFirebaseModel();

    private final MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private final MutableLiveData<List<FullPost>> postsWithUser = new MutableLiveData<>();
    private final MutableLiveData<List<Post>> userPosts = new MutableLiveData<>();
    final public MutableLiveData<LoadingState> postsListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    final public MutableLiveData<LoadingState> postsWithUserListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);
    final public MutableLiveData<LoadingState> userPostsListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);

    public static PostModel instance() {
        return instance;
    }

    public LiveData<List<FullPost>> getAllPostsWithUser() {
        if (postsWithUser.getValue() == null) refreshAllPostsWithUser();

        return postsWithUser;
    }

    public LiveData<List<Post>> getAllUserPosts() {
        if (userPosts.getValue() == null) refreshUserPosts((v) -> {});

        return userPosts;
    }

    public void refreshAllPosts(Listener listener) {
        postsListLoadingState.postValue(LoadingState.LOADING);
        postFirebaseModel.getAllPosts(list -> db.executor.execute(() -> {
            updatePostsInRoom(list);
            posts.postValue(db.getPostDao().getAll());
            postsListLoadingState.postValue(LoadingState.NOT_LOADING);
            listener.onComplete(null);
        }));
    }

    public void refreshAllPostsWithUser() {
        postsWithUserListLoadingState.postValue(LoadingState.LOADING);
        refreshUserPosts((v) -> db.executor.execute(() -> {
            List<FullPost> postsList = db.getPostDao().getPostsWithUser();
            postsWithUser.postValue(postsList);
            postsWithUserListLoadingState.postValue(LoadingState.NOT_LOADING);
        }));
    }

    public void refreshUserPosts(Listener listener) {
        userPostsListLoadingState.postValue(LoadingState.LOADING);
        UserModel.instance().refreshAllUsers((v) -> refreshAllPosts((v1) -> db.executor.execute(() -> {
            List<Post> userPosts = db.getPostDao().getPostsByUserId(UserModel.instance().getCurrentUserId());
            this.userPosts.postValue(userPosts);
            userPostsListLoadingState.postValue(LoadingState.NOT_LOADING);
            listener.onComplete(null);
        })));
    }

    private void updatePostsInRoom(List<Post> posts) {
        for (Post post : posts) {
            db.executor.execute(() -> db.getPostDao().insertAll(post));
        }
    }

    public void addPost(Post post) {
        postFirebaseModel.addPost(post, (v) -> {
            db.executor.execute(() -> db.getPostDao().insert(post));
            refreshAllPostsWithUser();
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        this.postFirebaseModel.uploadImage(name, bitmap, listener);
    }

    public void updatePost(Post post) {
        postFirebaseModel.updatePost(post, (v) -> db.executor.execute(() -> {
            db.getPostDao().update(post);
            refreshAllPostsWithUser();
        }));
    }
}
