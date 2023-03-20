package com.example.manipedi.createPost;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manipedi.DB.NailPolish;
import com.example.manipedi.DB.NailPolishAutoCompleteTask;
import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.DB.viewModels.AddPostViewModel;
import com.example.manipedi.ImageLaunchers;
import com.example.manipedi.databinding.FragmentCreatePostBinding;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class CreatePostFragment extends Fragment {
    private FragmentCreatePostBinding binding;
    private ImageLaunchers imageLaunchers;
    private NailPolish nailPolish;
    private AddPostViewModel addPostViewModel;

    private EditText location;
    private EditText description;
    private AutoCompleteTextView nailPolishAutoComplete;
    private ImageView nailPolishImage;
    private TextView nailPolishBrand;
    private TextView nailPolishName;
    private TextView nailPolishDescription;
    private Button createPostBtn;

    public CreatePostFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        initializeVariables();
        addOnClickListeners();

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        addPostViewModel = new ViewModelProvider(this).get(AddPostViewModel.class);
    }

    private void initializeVariables() {
        location = binding.createPostLocation;
        description = binding.createPostDescription;

        nailPolishAutoComplete = binding.createPostNailPolishAutoComplete;

        NailPolishAutoCompleteTask nailPolishTask = new NailPolishAutoCompleteTask(nailPolishAutoComplete);
        nailPolishTask.execute();

        nailPolishImage = binding.createPostNailPolishImg;
        nailPolishBrand = binding.createPostNailPolishBrand;
        nailPolishName = binding.createPostNailName;
        nailPolishDescription = binding.createPostNailPolishDescription;
        createPostBtn = binding.createPostCreateBtn;
        imageLaunchers = new ImageLaunchers(this, binding.createPostPostImage,
                binding.createPostFromGallery, binding.createPostTakePhoto);
    }

    private void addOnClickListeners() {
        createPostBtn.setOnClickListener((view) -> {
            if (!isPostValid()) Toast.makeText(getContext(), "Missing a field", Toast.LENGTH_SHORT).show();
            else createPost();
        });

        nailPolishAutoComplete.setOnItemClickListener((AdapterView<?> arg0, View arg1, int arg2, long arg3) -> {
            nailPolish = (NailPolish) arg0.getAdapter().getItem(arg2);
            Picasso.get().load(nailPolish.getImage()).into(nailPolishImage);

            nailPolishBrand.setVisibility(View.VISIBLE);
            nailPolishName.setVisibility(View.VISIBLE);
            nailPolishImage.setVisibility(View.VISIBLE);
            nailPolishDescription.setVisibility(View.VISIBLE);

            nailPolishBrand.setText(nailPolish.getBrand());
            nailPolishName.setText(nailPolish.getName());
            nailPolishDescription.setText(nailPolish.getDescription());
        });
    }

    private boolean isPostValid() {
        if (location.getText().toString().equals("") ||
                description.getText().toString().equals("") ||
                nailPolishAutoComplete.getText().toString().equals(""))
            return false;
        return true;
    }

    private void createPost() {
        Post post = new Post(UUID.randomUUID().toString(),
                UserModel.instance().getCurrentUserId(),
                location, "0", description, "", nailPolish);

        addPostViewModel.addPost(post, imageLaunchers.getPhoto());

        Toast.makeText(getContext(), "created post", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }
}