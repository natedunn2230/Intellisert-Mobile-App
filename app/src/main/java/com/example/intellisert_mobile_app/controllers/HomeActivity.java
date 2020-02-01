package com.example.intellisert_mobile_app.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.intellisert_mobile_app.R;
import com.example.intellisert_mobile_app.stuff.BaseView;
import com.example.intellisert_mobile_app.stuff.HomeView;


public class HomeActivity extends AppCompatActivity implements Controllable {

    private BaseView homeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // XML Layout File
        setContentView(R.layout.home_activity);

        // set reference to view class
        ViewGroup view = findViewById(android.R.id.content);
        homeView = new HomeView(view, getApplicationContext());

    }

    /**
     * Changes view to pairing page when start button clicked.
     */
    public void onButtonStartClick(View view){
        Log.println(Log.INFO, "user_click", "User clicked START button.");
    }

    /**
     * Changes view to about page when about button clicked.
     */
    public void onButtonAboutClick(View view) {
        Log.println(Log.INFO, "user_click", "User clicked ABOUT button.");

    }

    @Override
    public void updateView() {}
}
