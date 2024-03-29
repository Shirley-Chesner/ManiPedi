package com.example.manipedi.DB;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.manipedi.DB.firebase.UserAuthentication;
import com.example.manipedi.DB.firebase.UserFirebaseModel;
import com.example.manipedi.DB.room.DBImplementation;
import com.example.manipedi.DB.room.Schema.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserModel {
    private static UserModel instance;
    DBImplementation db = DBImplementation.instance();

    private final UserFirebaseModel firebaseUser = new UserFirebaseModel();

    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();
    public final MutableLiveData<LoadingState> usersListLoadingState = new MutableLiveData<>(LoadingState.NOT_LOADING);

    private UserModel() {}

    public static UserModel instance() {
        if (instance == null) instance = new UserModel();
        return instance;
    }

    public String getCurrentUserId() {
        return UserAuthentication.getInstance().getUserUid();
    }

    public boolean isSignedIn() {
        return UserAuthentication.getInstance().getUser() != null;
    }

    public void getSignedUser(Listener<LiveData<User>> listener) {
        if (currentUser.getValue() == null || !currentUser.getValue().getId().equals(getCurrentUserId())) {
            refreshAllUsers((v) -> listener.onComplete(currentUser));
        } else {
            listener.onComplete(currentUser);
        }
    }

    public void setSignedUser() {
        db.executor.execute(() -> {
            User user = db.getUserDao().findById(getCurrentUserId());
            if (user == null) {
                refreshAllUsers((v) -> postProfileUser(db.getUserDao().findById(getCurrentUserId())));
            } else {
                PostModel.instance().refreshUserPosts((v) -> postProfileUser(user));
            }
        });
    }

    private void postProfileUser(User user) {
        currentUser.postValue(user);
    }

    public void signUserOut() {
        UserAuthentication.getInstance().signOut();
        db.executor.execute(() -> {
            postProfileUser(null);
        });
    }

    public void refreshAllUsers(Listener listener) {
        usersListLoadingState.postValue(LoadingState.LOADING);

        firebaseUser.getAllUsersSince(list -> db.executor.execute(() -> {
            updateUsersInRoom(list);

            users.postValue(db.getUserDao().getAll());
            postProfileUser(db.getUserDao().findById(getCurrentUserId()));

            usersListLoadingState.postValue(LoadingState.NOT_LOADING);
            listener.onComplete(null);
        }));
    }

    private void updateUsersInRoom(List<User> users) {
        for (User user : users) {
            db.executor.execute(() ->  db.getUserDao().insertAll(user));
        }
    }


    public void addUser(User user) {
        firebaseUser.addUser(user, (Void) -> {
            db.executor.execute(() -> {
                db.getUserDao().insert(user);
                refreshAllUsers((v) -> {});
            });
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseUser.uploadImage(name, bitmap, listener);
    }

    public void updateUser(User user, Listener listener) {
        firebaseUser.updateUser(user, (Void) -> {
            db.executor.execute(() -> {
                db.getUserDao().update(user);
                PostModel.instance().refreshAllPostsWithUser();
            });
            listener.onComplete(null);
        });
    }
}
