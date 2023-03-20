package com.example.manipedi.homePageFragment;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manipedi.DB.Adapter.PostsListAdapter;
import com.example.manipedi.DB.room.Schema.PostWithUser;
import com.example.manipedi.DB.viewModels.HomePageViewModel;
import com.example.manipedi.databinding.FragmentHomePageBinding;

import java.util.LinkedList;
import java.util.List;

public class HomePageFragment extends Fragment {
    private FragmentHomePageBinding binding;
    private HomePageViewModel homePageViewModel;

    private PostsListAdapter adapter;
    private List<PostWithUser> posts = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        homePageViewModel = new HomePageViewModel();
        adapter = new PostsListAdapter(getLayoutInflater(), posts);
        binding.PostsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.PostsList.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.PostsList.setHasFixedSize(false);
        adapter = new PostsListAdapter(getLayoutInflater(), posts);
        binding.PostsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.PostsList.setAdapter(adapter);

        homePageViewModel.getAllPostsWithUser().observe(getViewLifecycleOwner(), (posts) -> {
            adapter.setPostsList(posts);
        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        reloadData();
    }
}