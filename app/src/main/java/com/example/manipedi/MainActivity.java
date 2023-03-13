package com.example.manipedi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.manipedi.firebase.UserAuthentication;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    private UserAuthentication mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);

        mAuth = UserAuthentication.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Authentication success.", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "user " + mAuth.getUser().getEmail());
        }
    }

    int fragmentMenuId = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_bar,menu);
        if (fragmentMenuId != 0){
            menu.removeItem(fragmentMenuId);
        }
        fragmentMenuId = 0;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.home_btn:
//                return true;
//            case R.id.search_btn:
//                return true;
//            case R.id.chat_btn:
//                return true;
//            case R.id.profile_btn:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return false;
    }
}