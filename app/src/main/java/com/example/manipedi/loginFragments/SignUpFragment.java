package com.example.manipedi.loginFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.firebase.UserFirebaseModel;
import com.example.manipedi.DB.room.ManiPediApplication;
import com.example.manipedi.DB.room.Schema.User;
import com.example.manipedi.ImageLaunchers;
import com.example.manipedi.MainActivity;
import com.example.manipedi.R;

import com.example.manipedi.DB.firebase.UserAuthentication;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class SignUpFragment extends Fragment {

    private LoginFragmentManager fragmentManager;
    private ImageLaunchers imageLaunchers;
    private CircularProgressIndicator progressIndicator;

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private Button signInBtn;

    public SignUpFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        fragmentManager = new LoginFragmentManager(getActivity());

        initializeVariables(view);
        initializeOnClickListeners();

        imageLaunchers = new ImageLaunchers(this, view.findViewById(R.id.signUpFragment_userImage),
                view.findViewById(R.id.signUpFragment_uploadPhoto), view.findViewById(R.id.signUpFragment_takePhoto));

        return view;
    }

    private void initializeVariables(View view) {
        email = view.findViewById(R.id.signUpFragment_EmailInput);
        password = view.findViewById(R.id.signUpFragment_PasswordInput);
        loginBtn = view.findViewById(R.id.signUpFragment_loginBtn);
        signInBtn = view.findViewById(R.id.signUpFragment_SignInBtn);
        progressIndicator = view.findViewById(R.id.signUpPage_spinner);
    }

    private void initializeOnClickListeners() {
        signInBtn.setOnClickListener(view1 -> fragmentManager.changeFragment(LoginFragment.class));
        loginBtn.setOnClickListener(view1 -> SignUp());
    }

    private void SignUp() {
        UserAuthentication userAuthentication = UserAuthentication.getInstance();
        progressIndicator.show();

        if (!userAuthentication.isEmailAndPasswordValid(email, password)) {
            progressIndicator.hide();
            Toast.makeText(getContext(), "Email or password is invalid", Toast.LENGTH_SHORT).show();
            return;
        };

        userAuthentication.signUp(email, password, firebaseUser -> {
            if (firebaseUser == null) {
                progressIndicator.hide();
                Toast.makeText(getContext(), "Firebase Authentication failed.", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(firebaseUser.getUid(), email, "");
            UserFirebaseModel userModal = new UserFirebaseModel();
            userModal.uploadImage(user.getEmail(), imageLaunchers.getPhoto(), url -> {
                if (url != null) user.setPhotoUrl(url);

                UserModel.instance().addUser(user);
            });

            UserModel.instance().setSignedUser();

            startActivity(new Intent(ManiPediApplication.getMyContext(), MainActivity.class));
        });
    }
}