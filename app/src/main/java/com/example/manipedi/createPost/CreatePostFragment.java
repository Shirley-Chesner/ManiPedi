package com.example.manipedi.createPost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manipedi.R;
import com.example.manipedi.databinding.FragmentCreatePostBinding;
import com.example.manipedi.databinding.FragmentUserPageBinding;


public class CreatePostFragment extends Fragment {
    private ViewBinding binding;

    public CreatePostFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}