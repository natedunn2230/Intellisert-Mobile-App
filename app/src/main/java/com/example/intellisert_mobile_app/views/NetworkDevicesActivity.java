package com.example.intellisert_mobile_app.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.intellisert_mobile_app.R;

public class NetworkDevicesActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_devices_activity);

        init();
        bindActions();
    }

    @Override
    public void init() {

    }

    @Override
    public void bindActions() {

    }
}
