package com.example.manipedi.homePageFragment;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manipedi.DB.Adapter.PostsListAdapter;
import com.example.manipedi.DB.viewModels.HomePageViewModel;
import com.example.manipedi.databinding.FragmentHomePageBinding;
import com.example.manipedi.DB.room.Schema.Post;

import java.util.LinkedList;
import java.util.List;

public class HomePageFragment extends Fragment {
    FragmentHomePageBinding binding;
    HomePageViewModel homePageViewModel;

    List<Post> data = new LinkedList<>();
    PostsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        homePageViewModel = new HomePageViewModel();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
//        reloadData();
    }
}