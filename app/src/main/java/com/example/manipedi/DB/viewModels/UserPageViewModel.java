package com.example.manipedi.DB.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.manipedi.DB.PostModel;
import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.DB.room.Schema.User;

import java.util.List;

public class UserPageViewModel extends ViewModel {
    private final LiveData<List<Post>> userPosts = PostModel.instance().getAllUserPosts();
    private LiveData<User> user = null;

    public UserPageViewModel() {
        UserModel.instance().getSignedUser(u -> user = u);
    }

    public LiveData<List<Post>> getUserPosts() {
        return userPosts;
    }

    public LiveData<User> getUser() {
        return user;
    }
}
