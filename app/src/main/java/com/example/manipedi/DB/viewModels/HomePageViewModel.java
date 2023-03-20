package com.example.manipedi.DB.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.manipedi.DB.PostModel;
import com.example.manipedi.DB.room.Schema.PostWithUser;

import java.util.List;

public class HomePageViewModel extends ViewModel {
    private final LiveData<List<PostWithUser>> postsWithUser = PostModel.instance().getAllPostsWithUser();

    public LiveData<List<PostWithUser>> getAllPostsWithUser() {
        return postsWithUser;
    }
}
