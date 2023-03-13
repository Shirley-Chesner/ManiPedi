package com.example.manipedi.DB.room.Adapter;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manipedi.DB.room.Schema.Post;
import com.example.manipedi.R;

import java.io.IOException;
import java.util.List;

class PostsListHolder extends RecyclerView.ViewHolder {
    TextView ownerName;
    ImageView ownerPic;
    TextView description;
    TextView score;
    TextView location;
    List<Post> data;

    public PostsListHolder(@NonNull View itemView, UsersListAdapter.OnItemClickListener listener, List<Post> data) {
        super(itemView);
        this.ownerName = itemView.findViewById(R.id.owner_name);
        this.ownerPic = itemView.findViewById(R.id.owner_icon);
        this.description = itemView.findViewById(R.id.description);
        this.score = itemView.findViewById(R.id.score_number);
        this.location = itemView.findViewById(R.id.owner_location);
        this.data = data;

        // Add onClick for showing pictures

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Post p, int pos) throws IOException {
//        URL myUrl = new URL(p.avatarURL);
//        InputStream inputStream = (InputStream)myUrl.getContent();
//        Drawable drawable = Drawable.createFromStream(inputStream, null);
        ownerName.setText(p.owner);
//        ownerPic.setImageDrawable(drawable);
        description.setText(p.description);
        score.setText(p.score); // TODO: find out what to do about the float
        location.setText(p.location);
//        cb.setTag(pos);
    }
}

public class PostsListAdapter extends RecyclerView.Adapter<PostsListHolder> {
    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Post> data;

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setData(List<Post> data){
        clearItems();
        addItems(data);
    }


    public void addItems(List<Post> newItems)
    {
        this.data.addAll(newItems);
        notifyDataSetChanged();
    }

    public void clearItems()
    {
        this.data.clear();
        notifyDataSetChanged();
    }


    public PostsListAdapter(LayoutInflater inflater, List<Post> data){
        this.inflater = inflater;
        this.data = data;
    }

    @NonNull
    @Override
    public PostsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_page_row,parent,false);
        return new PostsListHolder(view, (UsersListAdapter.OnItemClickListener) listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsListHolder holder, int position) {
        Post st = data.get(position);
        try {
            holder.bind(st,position);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
