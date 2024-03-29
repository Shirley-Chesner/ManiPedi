package com.example.manipedi.loginFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.room.ManiPediApplication;
import com.example.manipedi.MainActivity;
import com.example.manipedi.R;

import com.example.manipedi.DB.firebase.UserAuthentication;

public class LoginFragment extends Fragment {

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private Button signInBtn;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        LoginFragmentManager fragmentManager = new LoginFragmentManager(getActivity());
        UserAuthentication userAuthentication = UserAuthentication.getInstance();

        email = view.findViewById(R.id.loginFragment_EmailInput);
        password = view.findViewById(R.id.loginFragment_passwordInput);
        loginBtn = view.findViewById(R.id.loginFragment_loginBtn);
        signInBtn = view.findViewById(R.id.loginFragment_SignInBtn);

        loginBtn.setOnClickListener(view1 -> {
            if (!userAuthentication.isEmailAndPasswordValid(email, password)) {
                Toast.makeText(getContext(), "Email or password is invalid", Toast.LENGTH_SHORT).show();
                return;
            };

            userAuthentication.login(email, password, user -> {
                if (user != null) {
                    UserModel.instance().setSignedUser();
                    Toast.makeText(getContext(), "Authentication success.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ManiPediApplication.getMyContext(), MainActivity.class));
                } else {
                    Toast.makeText(getContext(), "Firebase Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        signInBtn.setOnClickListener(view1 -> {
            fragmentManager.changeFragment(SignUpFragment.class);
        });

        return view;
    }
}