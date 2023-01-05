package com.example.manipedi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        switch (item.getItemId()) {
            case R.id.home_btn:
                return true;
            case R.id.search_btn:
                return true;
            case R.id.chat_btn:
                return true;
            case R.id.profile_btn:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}