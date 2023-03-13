package com.example.manipedi.loginFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manipedi.MainActivity;
import com.example.manipedi.R;

import com.example.manipedi.firebase.UserAuthentication;

public class LoginFragment extends Fragment {

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        LoginFragmentManager fragmentManager = new LoginFragmentManager(getActivity());
        UserAuthentication userAuthentication = UserAuthentication.getInstance();

        EditText email = view.findViewById(R.id.loginFragment_EmailInput);
        EditText password = view.findViewById(R.id.loginFragment_passwordInput);
        Button loginBtn = view.findViewById(R.id.loginFragment_loginBtn);
        Button signInBtn = view.findViewById(R.id.loginFragment_SignInBtn);

        loginBtn.setOnClickListener(view1 -> {
            if (!userAuthentication.isEmailAndPasswordValid(email, password)) {
                Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                return;
            };

            userAuthentication.login(email, password, user -> {
                if (user != null) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("TAG", "user not created");
                }
            });
        });

        signInBtn.setOnClickListener(view1 -> {
            fragmentManager.changeFragment(SignUpFragment.class);
        });

        return view;
    }
}