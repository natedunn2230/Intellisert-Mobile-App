package com.example.intellisert_mobile_app.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intellisert_mobile_app.R;
import com.example.intellisert_mobile_app.controllers.HomeController;


public class HomeActivity extends AppCompatActivity implements BaseView {

    HomeController controller;
    Button buttonStart, buttonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // XML Layout File
        setContentView(R.layout.home_activity);

        controller = new HomeController(this);

        init();
        bindActions();
    }

    @Override
    public void init() {
        // buttons
        buttonStart = findViewById(R.id.home_button_start);
        buttonAbout = findViewById(R.id.home_button_about);
    }

    @Override
    public void bindActions() {
        // buttons
        buttonStart.setOnClickListener(view -> {
            Log.println(Log.INFO, "user_click", "User clicked START button.");
            controller.changeView(WifiCredentialsActivity.class);
        });

        buttonAbout.setOnClickListener(view -> {
            Log.println(Log.INFO, "user_click", "User clicked ABOUT button.");
        });
    }
}
