package com.example.manipedi.DB.firebase;

import android.widget.EditText;

import com.example.manipedi.DB.Listener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UserAuthentication {
    private static UserAuthentication instance;

    private FirebaseAuth mAuth;

    private UserAuthentication() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static UserAuthentication getInstance() {
        if (instance == null) instance = new UserAuthentication();

        return instance;
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public String getUserUid() {
        FirebaseUser user = getUser();
        if (user != null) {
            return Objects.requireNonNull(user).getUid();
        }
        return null;
    }

    public boolean isEmailAndPasswordValid(EditText email, EditText password) {
        return this.isEmailAndPasswordValid(email.getText().toString(), password.getText().toString());
    }

    public boolean isEmailAndPasswordValid(String email, String password) {
        if (email.length() == 0 || password.length() == 0) return false;
        return true;
    }

    public void signUp(EditText email, EditText password, Listener<FirebaseUser> onCreateUser) {
        this.signUp(email.getText().toString(), password.getText().toString(), onCreateUser);
    }

    public void signUp(String email, String password, Listener<FirebaseUser> onCreateUser) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task ->
                        this.onCompleteListener(task, onCreateUser));
    }

    public void login(EditText email, EditText password, Listener<FirebaseUser> onCreateUser) {
        this.login(email.getText().toString(), password.getText().toString(), onCreateUser);
    }

    public void login(String email, String password, Listener<FirebaseUser> onCreateUser) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->
            this.onCompleteListener(task, onCreateUser));
    }

    public void signOut() {
        mAuth.signOut();
    }

    void onCompleteListener(Task task, Listener<FirebaseUser> onCreateUser) {
        if (task.isSuccessful()) {
            FirebaseUser user = mAuth.getCurrentUser();
            onCreateUser.onComplete(user);
        } else {
            onCreateUser.onComplete(null);
        }
    }

}

