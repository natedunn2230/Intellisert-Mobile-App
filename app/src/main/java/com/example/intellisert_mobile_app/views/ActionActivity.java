package com.example.intellisert_mobile_app.views;

import android.os.Bundle;

import com.example.intellisert_mobile_app.controllers.ActionController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.intellisert_mobile_app.R;

public class ActionActivity extends AppCompatActivity implements BaseView {

    private ToggleButton toggleLight;
    private final String ACTION_ACTIVITY = "ACTION_ACTIVITY";

    private ActionController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_activity);
        init();
        bindActions();

        // get initial light state
        Thread thread = new Thread(() -> {
            controller.fetchLightState();
        });

        thread.start();
    }

    @Override
    public void init() {
        toggleLight = findViewById(R.id.action_toggle_on);

        controller = new ActionController(this);
    }

    @Override
    public void bindActions() {
        toggleLight.setOnClickListener((r) -> {

            Thread thread = new Thread(() -> {
             controller.setLightPower();
            });

            thread.start();
        });
    }

    /**
     * Toggles the light button based on the state of the Intellisert's light
     * @param lightState - state of the Intellisert's light
     */
    public void setLightButtonState(String lightState){
        runOnUiThread(() -> {
            toggleLight.setChecked(lightState.equals("on"));
        });
    }
}