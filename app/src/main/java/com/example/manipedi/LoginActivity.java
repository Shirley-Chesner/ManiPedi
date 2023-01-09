package com.example.manipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.manipedi.loginFragments.LoginFragmentManager;
import com.example.manipedi.loginFragments.LoginHomePageFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragmentManager loginFragmentManager = new LoginFragmentManager(this);
        loginFragmentManager.changeFragment(LoginHomePageFragment.class);

    }
}