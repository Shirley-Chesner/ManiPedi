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

import com.example.manipedi.MainActivity;
import com.example.manipedi.R;

import dataBases.UserAuthentication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    public SignUpFragment() {}

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        LoginFragmentManager fragmentManager = new LoginFragmentManager(getActivity());
        UserAuthentication userAuthentication = UserAuthentication.getInstance();

        EditText email = view.findViewById(R.id.signUpFragment_EmailInput);
        EditText password = view.findViewById(R.id.signUpFragment_PasswordInput);
        Button loginBtn = view.findViewById(R.id.signUpFragment_loginBtn);
        Button signInBtn = view.findViewById(R.id.signUpFragment_SignInBtn);

        loginBtn.setOnClickListener(view1 -> {
            if (!userAuthentication.isEmailAndPasswordValid(email, password)) return;

            userAuthentication.signUp(email, password, user -> {
                if (user != null) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("TAG", "user not created");
                }
            });
        });

        signInBtn.setOnClickListener(view1 -> {
            fragmentManager.changeFragment(LoginFragment.class);
        });

        return  view;
    }
}