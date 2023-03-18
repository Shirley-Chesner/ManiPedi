package com.example.manipedi.DB.viewModels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.manipedi.DB.PostModel;
import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.DB.room.Schema.User;

public class AddPostViewModel extends ViewModel {
    private LiveData<User> user = null;

    public AddPostViewModel() {
        UserModel.instance().getSignedUser((u) -> user = u);
    }

    public LiveData<User> getUser(){
        return user;
    }

    public void addPost(Post post, Bitmap postImageBitmap) {
        PostModel.instance().uploadImage(post.getId(), postImageBitmap, url -> {
            setPostImage(post, url);
            PostModel.instance().addPost(post);
        });
    }

    private void setPostImage(Post post, String url) {
        if (url != null) {
            post.setImage(url);
        }
    }
}
