package com.example.manipedi.DB.Adapter;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manipedi.DB.NailPolishPostTask;
import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class PostsListHolder extends RecyclerView.ViewHolder {
    private TextView ownerName;
    private ImageView ownerPic;
    private TextView description;
    private TextView score;
    private TextView location;
    private TextView nailBrand;
    private TextView nailName;
    private TextView nailDescription;
    private ImageView nailImage;
    private List<Post> posts;

    public PostsListHolder(@NonNull View itemView, List<Post> data) {
        super(itemView);
        ownerName = itemView.findViewById(R.id.post_user_name);
        ownerPic = itemView.findViewById(R.id.post_user_image);
        description = itemView.findViewById(R.id.user_post_description);
        score = itemView.findViewById(R.id.user_score_number);
        location = itemView.findViewById(R.id.user_post_location);
        nailBrand = itemView.findViewById(R.id.homePage_nailPolishBrand);
        nailName = itemView.findViewById(R.id.homePage_nailPolishName);
        nailDescription = itemView.findViewById(R.id.homePage_nailPolishDescription);
        nailImage = itemView.findViewById(R.id.homePage_nailPolishImage);
        posts = data;

    }

    public void bind(Post post) {
        ownerName.setText(post.owner);
        description.setText(post.description);
        score.setText(post.score);
        location.setText(post.location);
        Picasso.get().load(post.getImage()).into(ownerPic);
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

public class PostsListAdapter extends RecyclerView.Adapter<PostsListHolder> {
    LayoutInflater inflater;
    List<Post> data;

    public PostsListAdapter(LayoutInflater inflater, List<Post> data){
        this.inflater = inflater;
        this.data = data;
    }

    public void setPostsList(List<Post> newItems) {
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
        View view = inflater.inflate(R.layout.home_page_row, parent,false);
        return new PostsListHolder(view, data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsListHolder holder, int position) {
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
