package com.example.manipedi.userPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.room.Schema.User;
import com.example.manipedi.ImageLaunchers;
import com.example.manipedi.databinding.FragmentEditUserBinding;

import java.util.UUID;

public class editUserFragment extends Fragment {

    private static final String ARG_USER = "user";
    private User user;

    private EditText firstName;
    private EditText lastName;
    private Button updateButton;

    private FragmentEditUserBinding binding;
    private ImageLaunchers imageLaunchers;

    public editUserFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User)getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        firstName = binding.editUserFragmentFirstName;
        lastName = binding.editUserFragmentLastName;
        updateButton = binding.editUserFragmentEditBtn;

        imageLaunchers = new ImageLaunchers(this, binding.editUserFragmentUserImage, binding.editUserFragmentUploadPhoto, binding.editUserFragmentTakePhoto);
        initializeData();

        return binding.getRoot();
    }

    private void initializeData() {
        imageLaunchers.loadImage(user.getPhotoUrl());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        updateButton.setOnClickListener((v) -> {
            if (firstName.getText().toString().equals("") || lastName.getText().toString().equals("")) {
                Toast.makeText(getContext(), "field is missing", Toast.LENGTH_SHORT).show();
                return;
            }

            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());

            UserModel.instance().uploadImage(UUID.randomUUID().toString(), imageLaunchers.getPhoto(), url -> {
                user.setPhotoUrl(url);
                UserModel.instance().updateUser(user, (vv) -> {
                    Toast.makeText(getContext(), "updated post", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(binding.getRoot()).popBackStack();
                });
            });

        });
    }
}