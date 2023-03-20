package com.example.manipedi.userPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manipedi.DB.Adapter.UserPagePostRecyclerAdapter;
import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.DB.room.Schema.User;
import com.example.manipedi.DB.viewModels.UserPageViewModel;
import com.example.manipedi.LoginActivity;
import com.example.manipedi.DB.firebase.UserAuthentication;
import com.example.manipedi.databinding.FragmentUserPageBinding;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class UserPageFragment extends Fragment {
    UserAuthentication mAuth;
    FragmentUserPageBinding binding;

    static final String USER_KEY = "user";
    private UserPageViewModel userPageViewModel;
    private User user;
    private List<Post> userPosts = new LinkedList<>();

    private Button logOutBtn;
    private ImageView userImage;
    private TextView userName;
    private RecyclerView recyclerView;
    private UserPagePostRecyclerAdapter adapter;

    public UserPageFragment() {}

    public static UserPageFragment newInstance(User user) {
        UserPageFragment fragment = new UserPageFragment();
        Bundle args = new Bundle();

        args.putSerializable(USER_KEY, user);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserPageBinding.inflate(inflater, container, false);

        mAuth = UserAuthentication.getInstance();

        initializeVariables();

        logOutBtn.setOnClickListener(view1 -> {
            UserModel.instance().signUserOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initializeVariables();
        initializeRecycleView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userPageViewModel = new ViewModelProvider(this).get(UserPageViewModel.class);
    }

    private void initializeVariables() {
        logOutBtn = binding.userPageLogOutBtn;
        userImage = binding.userPageFragmentUserImage;
        userName = binding.userPageFragmentUserName;
        recyclerView = binding.userPageFragmentUserPostsList;
        initializeUser();
        userName.setText(user.getEmail());
        Picasso.get().load(user.getPhotoUrl()).into(userImage);
    }

    private void initializeUser() {
        if (user == null) {
            UserModel.instance().getSignedUser(u -> {
                user = u.getValue();
            });
        } //(User)getArguments().getSerializable(USER_KEY);
        else {
//            userPageViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                Log.d("Shirley", user.getEmail());
                Log.d("Shirley", user.getPhotoUrl());
//                this.user = user;
                userName.setText(user.getEmail());
                Picasso.get().load(user.getPhotoUrl()).into(userImage);
//            });
        }
    }

    private void initializeRecycleView() {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserPagePostRecyclerAdapter(getLayoutInflater(), userPosts);
        recyclerView.setAdapter(adapter);

        userPageViewModel.getUserPosts().observe(getViewLifecycleOwner(), (posts) -> {
            userPosts = posts;
            adapter.setPostsList(userPosts);
        });
    }
}