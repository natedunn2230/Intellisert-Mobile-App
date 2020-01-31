package com.example.intellisert_mobile_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.intellisert_mobile_app.R;

public class HomeActivity extends AppCompatActivity {

    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }

    /**
     * Initializes the activity component's references
     */
    private void initialize(){
        homeButton = (Button)findViewById(R.id.button_start);
    }
}
