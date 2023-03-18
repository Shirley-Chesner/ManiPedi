package com.example.manipedi.DB.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manipedi.DB.NailPolishPostTask;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class UserProfilePostViewHolder extends RecyclerView.ViewHolder {
    private Post post;
    private ImageView image;
    private TextView description;
    private TextView location;
    private TextView nailBrand;
    private TextView nailName;
    private TextView nailDescription;
    private ImageView nailImage;
    private ImageButton editButton;

    public UserProfilePostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.image = itemView.findViewById(R.id.post_image);
        this.description = itemView.findViewById(R.id.post_description);
        this.location = itemView.findViewById(R.id.post_location);
        this.editButton = itemView.findViewById(R.id.userPageRow_editBtn);
        this.nailBrand = itemView.findViewById(R.id.userPagePost_nailPolishBrand);
        this.nailDescription = itemView.findViewById(R.id.userPagePost_nailPolishDescription);
        this.nailImage = itemView.findViewById(R.id.userPagePost_nailPolishImage);
        this.nailName = itemView.findViewById(R.id.userPagePost_nailPolishName);

//        this.editButton.setOnClickListener(v -> Navigation.findNavController(itemView)
//                .navigate(UserProfileFragmentDirections.actionUserProfileFragmentToEditPostFragment(post)));
    }

    public void bind(Post post) {
        this.post = post;
        Picasso.get().load(post.getImage()).into(image);
        description.setText(post.getDescription());
        location.setText(post.getLocation());
        // add loading
        NailPolishPostTask nailPolishPostTask = new NailPolishPostTask(post.nailPolishUrl ,(nailPolish) -> {
            nailName.setText(nailPolish.getName().trim());
            nailDescription.setText(nailPolish.getDescription().trim());
            nailBrand.setText(nailPolish.getBrand().trim());
            Picasso.get().load(nailPolish.getImage()).into(nailImage);
        });
        nailPolishPostTask.execute();
    }
}

public class UserPagePostRecyclerAdapter extends RecyclerView.Adapter<UserProfilePostViewHolder> {
    LayoutInflater inflater;
    List<Post> data;

    public UserPagePostRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setPostsList(List<Post> postsList) {
        this.data = postsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.user_page_row;
    }

    @NonNull
    @Override
    public UserProfilePostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new UserProfilePostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfilePostViewHolder holder, int position) {
        if (!data.isEmpty()) {
            Post post = data.get(position);
            holder.bind(post);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
