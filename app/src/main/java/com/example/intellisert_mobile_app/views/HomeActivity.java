package com.example.intellisert_mobile_app.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intellisert_mobile_app.R;
import com.example.intellisert_mobile_app.controllers.HomeController;


public class HomeActivity extends AppCompatActivity implements BaseView {

    private HomeController controller;
    private Button buttonStart, buttonAbout;

    private final String HOME_ACTIVITY = "HOME_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // XML Layout File
        setContentView(R.layout.home_activity);

        init();
        bindActions();
    }

    @Override
    public void init() {
        controller = new HomeController(this);

        // buttons
        buttonStart = findViewById(R.id.home_button_start);
        buttonAbout = findViewById(R.id.home_button_about);
    }

    @Override
    public void bindActions() {
        // buttons
        buttonStart.setOnClickListener(view -> {
            Log.d(HOME_ACTIVITY, "User clicked START button.");
            controller.changeView(BluetoothPairActivity.class);
        });

        buttonAbout.setOnClickListener(view -> {
            Log.d(HOME_ACTIVITY, "User clicked ABOUT button.");
        });
    }
}
