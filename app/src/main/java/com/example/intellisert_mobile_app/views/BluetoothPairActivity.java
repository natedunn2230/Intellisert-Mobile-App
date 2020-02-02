package com.example.intellisert_mobile_app.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intellisert_mobile_app.R;
import com.example.intellisert_mobile_app.controllers.BluetoothPairController;

public class BluetoothPairActivity extends AppCompatActivity implements BaseView {

    private BluetoothPairController controller;
    private Button buttonPair;
    private LinearLayout deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_pair_activity);
        init();
        bindActions();
    }

    @Override
    public void init() {
        controller = new BluetoothPairController(this);

        // buttons
        buttonPair = findViewById(R.id.bluetooth_pair_button_begin);

        // bluetooth device list
        deviceList = findViewById(R.id.bluetooth_device_list);
        deviceList.setVisibility(View.INVISIBLE);
        deviceList.setEnabled(false);
    }

    @Override
    public void bindActions() {
        // buttons
        buttonPair.setOnClickListener(v -> {
            controller.startBluetooth();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // dispatch controller actions based on intents fired from the controller
        switch(requestCode){
            case BluetoothPairController.REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    controller.discoverDevices();
                }
                break;
        }
    }

    /**
     * Hides pair button and sets the list visible.
     */
    public void showList(){
        deviceList.setVisibility(View.VISIBLE);
        deviceList.setEnabled(true);
        buttonPair.setVisibility(View.INVISIBLE);
        buttonPair.setEnabled(false);
    }
}
