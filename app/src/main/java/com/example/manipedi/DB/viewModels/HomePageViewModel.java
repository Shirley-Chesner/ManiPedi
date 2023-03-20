package com.example.manipedi.DB.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.manipedi.DB.PostModel;
import com.example.manipedi.DB.room.Schema.FullPost;

import java.util.List;

public class HomePageViewModel extends ViewModel {
    private final LiveData<List<FullPost>> postsWithUser = PostModel.instance().getAllPostsWithUser();

    public LiveData<List<FullPost>> getAllPostsWithUser() {
        return postsWithUser;
    }
}
