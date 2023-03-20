package com.example.manipedi.DB.Adapter;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manipedi.DB.NailPolishPostTask;
import com.example.manipedi.DB.room.Schema.FullPost;
import com.example.manipedi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class PostsListHolder extends RecyclerView.ViewHolder {
    private TextView ownerName;
    private ImageView ownerPic;
    private ImageView postImage;
    private TextView description;
    private TextView score;
    private TextView location;
    private TextView nailBrand;
    private TextView nailName;
    private TextView nailDescription;
    private ImageView nailImage;

    public PostsListHolder(@NonNull View itemView, List<FullPost> data) {
        super(itemView);
        ownerName = itemView.findViewById(R.id.post_user_name);
        ownerPic = itemView.findViewById(R.id.post_user_image);
        postImage = itemView.findViewById(R.id.post_extra_image);
        description = itemView.findViewById(R.id.user_post_description);
        score = itemView.findViewById(R.id.user_score_number);
        location = itemView.findViewById(R.id.user_post_location);
        nailBrand = itemView.findViewById(R.id.homePage_nailPolishBrand);
        nailName = itemView.findViewById(R.id.homePage_nailPolishName);
        nailDescription = itemView.findViewById(R.id.homePage_nailPolishDescription);
        nailImage = itemView.findViewById(R.id.homePage_nailPolishImage);

    }

    public void bind(FullPost post) {
        ownerName.setText(post.user.getName());
        description.setText(post.post.getDescription());
        score.setText(post.post.getScore());
        location.setText(post.post.getLocation());
        NailPolishPostTask nailPolishPostTask = new NailPolishPostTask(post.post.getNailPolishUrl() ,(nailPolish) -> {
            nailName.setText(nailPolish.getName().trim());
            nailDescription.setText(nailPolish.getDescription().trim());
            nailBrand.setText(nailPolish.getBrand().trim());
            Picasso.get().load(nailPolish.getImage()).into(nailImage);
        });
        nailPolishPostTask.execute();
        Picasso.get().load(post.user.getPhotoUrl()).into(ownerPic);
        Picasso.get().load(post.post.getImage()).into(postImage);
    }
}

public class PostsListAdapter extends RecyclerView.Adapter<PostsListHolder> {
    LayoutInflater inflater;
    List<FullPost> data;

    public PostsListAdapter(LayoutInflater inflater, List<FullPost> data){
        this.inflater = inflater;
        this.data = data;
    }

    public void setPostsList(List<FullPost> newItems) {
        data = newItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.home_page_row;
    }

    @NonNull
    @Override
    public PostsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsListHolder(inflater.inflate(R.layout.home_page_row, parent,false), data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsListHolder holder, int position) {
        if (!data.isEmpty()) holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
