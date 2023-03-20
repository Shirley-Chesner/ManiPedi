package com.example.manipedi.createPost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.manipedi.DB.NailPolishPostTask;
import com.example.manipedi.DB.PostModel;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.ImageLaunchers;
import com.example.manipedi.databinding.FragmentEditPostBinding;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class EditPostFragment extends Fragment {
    private FragmentEditPostBinding binding;
    private ImageLaunchers imageLaunchers;
    private NailPolish nailPolish;
    private Post oldPost;

    private EditText location;
    private EditText description;
    private AutoCompleteTextView nailPolishAutoComplete;
    private ImageView nailPolishImage;
    private TextView nailPolishBrand;
    private TextView nailPolishName;
    private TextView nailPolishDescription;
    private Button editPostBtn;

    private static final String ARG_POST = "post";
    private static final String ARG_NAIL_POLISH = "nailPolish";

    public EditPostFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditPostBinding.inflate(inflater, container, false);
        location = binding.editPostLocation;
        description = binding.editPostDescription;

        nailPolishImage = binding.editPostNailPolishImg;
        nailPolishBrand = binding.editPostNailPolishBrand;
        nailPolishName = binding.editPostNailName;
        nailPolishDescription = binding.editPostNailPolishDescription;
        editPostBtn = binding.editPostEditBtn;
        imageLaunchers = new ImageLaunchers(this, binding.editPostPostImage,
                binding.editPostFromGallery, binding.editPostTakePhoto);
        nailPolishAutoComplete = binding.editPostNailPolishAutoComplete;

        initializeVariables();
        addOnClickListeners();

        return binding.getRoot();
    }

    private void initializeVariables() {
        NailPolishAutoCompleteTask nailPolishTask = new NailPolishAutoCompleteTask(nailPolishAutoComplete);
        nailPolishTask.execute();

        if (getArguments() != null) {
            oldPost = (Post) getArguments().getSerializable(ARG_POST);

            location.setText(oldPost.getLocation());
            description.setText(oldPost.getDescription());
            imageLaunchers.loadImage(oldPost.getImage());

            NailPolish oldNailPolish = (NailPolish) getArguments().getSerializable(ARG_NAIL_POLISH);
            if (oldNailPolish == null)  {
                NailPolishPostTask nailPolishPostTask = new NailPolishPostTask(oldPost.getNailPolishUrl(), (nailPolish) ->{
                    initializeNailPolishContent(nailPolish);
                    nailPolishAutoComplete.setText(nailPolish.toString());
                    this.nailPolish = nailPolish;
                });
                nailPolishPostTask.execute();
            } else {
                initializeNailPolishContent(oldNailPolish);
                nailPolishAutoComplete.setText(oldNailPolish.toString());
                nailPolish = oldNailPolish;
            }
        }
    }

    private void addOnClickListeners() {
        editPostBtn.setOnClickListener((view) -> {
            if (!isPostValid()) Toast.makeText(getContext(), "Missing a field", Toast.LENGTH_SHORT).show();
            else updatePost();
        });

        nailPolishAutoComplete.setOnItemClickListener((AdapterView<?> arg0, View arg1, int arg2, long arg3) -> {
            nailPolish = (NailPolish) arg0.getAdapter().getItem(arg2);
            initializeNailPolishContent(nailPolish);
        });
    }

    private boolean isPostValid() {
        if (location.getText().toString().equals("") ||
                description.getText().toString().equals("") ||
                nailPolishAutoComplete.getText().toString().equals(""))
            return false;
        return true;
    }

    private void updatePost() {
        Post post = new Post(oldPost.getId(),
                oldPost.getOwner(),
                location, oldPost.getScore(), description, "", nailPolish);

        PostModel.instance().uploadImage(UUID.randomUUID().toString(), imageLaunchers.getPhoto(), url -> {
            post.setImage(url);
            PostModel.instance().updatePost(post);
            Toast.makeText(getContext(), "updated post", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });
    }

    private void initializeNailPolishContent(NailPolish nailPolish) {
        Picasso.get().load(nailPolish.getImage()).into(nailPolishImage);

        nailPolishBrand.setVisibility(View.VISIBLE);
        nailPolishName.setVisibility(View.VISIBLE);
        nailPolishImage.setVisibility(View.VISIBLE);
        nailPolishDescription.setVisibility(View.VISIBLE);

        nailPolishBrand.setText(nailPolish.getBrand());
        nailPolishName.setText(nailPolish.getName());
        nailPolishDescription.setText(nailPolish.getDescription());
    }
}