package com.example.manipedi;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manipedi.model.Post;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

class PostsListHolder extends RecyclerView.ViewHolder {
    TextView ownerName;
    ImageView ownerPic;
    EditText description;
    EditText score;
    EditText location;
    List<Post> data;

    public PostsListHolder(@NonNull View itemView, PostsListAdapter.OnItemClickListener listener, List<Post> data) {
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
        URL myUrl = new URL(p.avatarURL);
        InputStream inputStream = (InputStream)myUrl.getContent();
        Drawable drawable = Drawable.createFromStream(inputStream, null);

        ownerName.setText(p.owner);
        ownerPic.setImageDrawable(drawable);
        description.setText(p.description);
        score.setText((int) p.score); // TODO: find out what to do about the float
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
        this.data = data;
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
        return new PostsListHolder(view,listener, data);
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
