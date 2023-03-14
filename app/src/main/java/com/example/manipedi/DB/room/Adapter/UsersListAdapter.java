package com.example.manipedi.DB.room.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manipedi.DB.room.Schema.User;
import com.example.manipedi.R;

import java.io.IOException;
import java.util.List;

class UsersListHolder extends RecyclerView.ViewHolder {
    TextView ownerName;
    ImageView ownerPic;
    TextView description;
    TextView score;
    TextView location;
    List<User> data;

    public UsersListHolder(@NonNull View itemView, UsersListAdapter.OnItemClickListener listener, List<User> data) {
        super(itemView);
//       this.id = itemView.findViewById(R.id.);
//        private String firstName;
//        private String lastName;
//        private String email;
//        private String photoUrl;
//        this.ownerName = itemView.findViewById(R.id.owner_name);
//        this.ownerPic = itemView.findViewById(R.id.owner_icon);
//        this.description = itemView.findViewById(R.id.description);
//        this.score = itemView.findViewById(R.id.score_number);
//        this.location = itemView.findViewById(R.id.owner_location);
//        this.data = data;

        // Add onClick for showing pictures

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(User p, int pos) throws IOException {
        // TODO: get info from profile
////        URL myUrl = new URL(p.avatarURL);
////        InputStream inputStream = (InputStream)myUrl.getContent();
////        Drawable drawable = Drawable.createFromStream(inputStream, null);
//        ownerName.setText(p.owner);
////        ownerPic.setImageDrawable(drawable);
//        description.setText(p.description);
//        score.setText(p.score);
//        location.setText(p.location);
////        cb.setTag(pos);
    }
}

public class UsersListAdapter extends RecyclerView.Adapter<UsersListHolder> {
    OnItemClickListener listener;
    LayoutInflater inflater;
    List<User> data;

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setData(List<User> data){
        clearItems();
        addItems(data);
    }


    public void addItems(List<User> newItems)
    {
        this.data.addAll(newItems);
        notifyDataSetChanged();
    }

    public void clearItems()
    {
        this.data.clear();
        notifyDataSetChanged();
    }


    public UsersListAdapter(LayoutInflater inflater, List<User> data){
        this.inflater = inflater;
        this.data = data;
    }


    @NonNull
    @Override
    public UsersListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_page_row,parent,false);
        return new UsersListHolder(view,listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersListHolder holder, int position) {
        User st = data.get(position);
        try {
            holder.bind(st, position);
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
