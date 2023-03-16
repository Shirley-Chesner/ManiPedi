package com.example.manipedi.DB.firebase;

import com.example.manipedi.DB.Listener;
import com.example.manipedi.DB.room.Schema.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserFirebase extends FirebaseModel {
    private FirebaseAuth mAuth;

    public UserFirebase() {
        super();
        mAuth = FirebaseAuth.getInstance();
    }

    public void getAllUsersSince(Listener<List<User>> callback) { //Long since,
        db.collection(User.COLLECTION)
//                .whereGreaterThanOrEqualTo(User.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<User> list = new LinkedList<>();
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonList = task.getResult();
                        for (DocumentSnapshot json : jsonList) {
                            list.add(User.fromJson(Objects.requireNonNull(json.getData())));
                        }
                    }
                    callback.onComplete(list);
                });
    }

    public void addUser(User user, Listener<Void> listener) {
        db.collection(User.COLLECTION).document(user.getId()).set(user.toJson())
                .addOnCompleteListener(task -> listener.onComplete(null));
    }

    public void getUserById(String id, Listener<User> callback) {
        db.collection(User.COLLECTION)
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(task -> {
                    User user = null;
                    if (task.isSuccessful()) {
                        QuerySnapshot jsonList = task.getResult();
                        for (DocumentSnapshot json : jsonList) {
                            user = User.fromJson(Objects.requireNonNull(json.getData()));
                        }
                    }
                    callback.onComplete(user);
                });
    }

    public void updateUser(User user, Listener<Void> callback) {
        Map<String, Object> jsonUser = user.toJson();
        db.collection("Users")
                .document(user.getId())
                .update(jsonUser)
                .addOnSuccessListener(unused -> callback.onComplete(null));
    }
}
