package com.example.manipedi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.manipedi.DB.UserModel;
import com.example.manipedi.DB.firebase.UserAuthentication;
import com.example.manipedi.DB.room.Schema.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener(( controller,destination,  arguments) -> {
            if (destination.getId() == R.id.userPageFragment) {
                User user = UserModel.instance().getSignedUser().getValue();
                destination.addArgument("user",  new NavArgument.Builder()
                        .setDefaultValue(user)
                        .build());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (UserAuthentication.getInstance().getUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            UserModel.instance().setSignedUser();
            Toast.makeText(this, "Authentication success.", Toast.LENGTH_SHORT).show();
        }
    }
}