package com.example.manipedi.DB.firebase;

import com.example.manipedi.DB.Listener;
import com.example.manipedi.DB.room.Schema.Post;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PostFirebaseModel extends FirebaseModel {
    private final String COLLECTION_NAME = "Posts";

    public PostFirebaseModel() {
        super();
    }

    public void getAllPostsSince(Long since, Listener<List<Post>> callback) {
        db.collection(COLLECTION_NAME)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<Post> list = new LinkedList<>();
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonList = task.getResult();
                        for (DocumentSnapshot json : jsonList) {
                            list.add(new Post(Objects.requireNonNull(json.getData())));
                        }
                    }
                    callback.onComplete(list);
                });
    }

    public void addPost(Post post, Listener<Void> listener) {
        db.collection(COLLECTION_NAME).document(post.getId()).set(post.toObject())
                .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void updatePost(Post post, Listener<Void> callback) {
        db.collection("Posts")
                .document(post.getId())
                .update(post.toObject())
                .addOnSuccessListener(unused -> callback.onComplete(null));
    }
}
