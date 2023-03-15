package com.example.manipedi.userPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.manipedi.LoginActivity;
import com.example.manipedi.R;
import com.example.manipedi.DB.firebase.UserAuthentication;


public class UserPageFragment extends Fragment {


    public UserPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_page, container, false);

        Button logOutBtn = view.findViewById(R.id.userPage_logOutBtn);
        logOutBtn.setOnClickListener(view1 -> {
            UserAuthentication.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        return view;
    }
}