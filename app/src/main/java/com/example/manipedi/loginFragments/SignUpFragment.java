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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.firebase.UserFirebaseModel;
import com.example.manipedi.DB.room.ManiPediApplication;
import com.example.manipedi.DB.room.Schema.User;
import com.example.manipedi.MainActivity;
import com.example.manipedi.R;

import com.example.manipedi.DB.firebase.UserAuthentication;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class SignUpFragment extends Fragment {

    private LoginFragmentManager fragmentManager;
    private ActivityResultLauncher<Void> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    private CircularProgressIndicator progressIndicator;

    private EditText email;
    private EditText password;
    private Button loginBtn;
    private Button signInBtn;
    private ImageButton uploadImageBtn;
    private ImageButton takePhotoBtn;
    private ImageView userImage;

    public SignUpFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        fragmentManager = new LoginFragmentManager(getActivity());

        initializeVariables(view);
        initializeOnClickListeners();
        initializeImageLaunchers();

        return view;
    }

    private void initializeVariables(View view) {
        email = view.findViewById(R.id.signUpFragment_EmailInput);
        password = view.findViewById(R.id.signUpFragment_PasswordInput);
        loginBtn = view.findViewById(R.id.signUpFragment_loginBtn);
        signInBtn = view.findViewById(R.id.signUpFragment_SignInBtn);
        uploadImageBtn = view.findViewById(R.id.signUpFragment_uploadPhoto);
        takePhotoBtn = view.findViewById(R.id.signUpFragment_takePhoto);
        userImage = view.findViewById(R.id.signUpFragment_userImage);

        progressIndicator = view.findViewById(R.id.signUpPage_spinner);
    }

    private void initializeOnClickListeners() {
        takePhotoBtn.setOnClickListener(view1 -> cameraLauncher.launch(null));
        uploadImageBtn.setOnClickListener(view1 -> galleryLauncher.launch("image/*"));
        signInBtn.setOnClickListener(view1 -> fragmentManager.changeFragment(LoginFragment.class));
        loginBtn.setOnClickListener(view1 -> SignUp());
    }

    private void initializeImageLaunchers() {
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), result -> {
            if (result != null) userImage.setImageBitmap(result);
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) userImage.setImageURI(result);
        });
    }

    private Bitmap getPhoto() {
        userImage.setDrawingCacheEnabled(true);
        userImage.buildDrawingCache();

        return ((BitmapDrawable) userImage.getDrawable()).getBitmap();
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
            userModal.uploadImage(user.getEmail(), getPhoto(), url -> {
                if (url != null) user.setPhotoUrl(url);

                UserModel.instance().addUser(user, (u) -> {});
            });

            Log.d("SHIRELY", user == null ? "user is null in login": user.getEmail());
            UserModel.instance().setSignedUser();

            startActivity(new Intent(ManiPediApplication.getMyContext(), MainActivity.class));
        });
    }
}