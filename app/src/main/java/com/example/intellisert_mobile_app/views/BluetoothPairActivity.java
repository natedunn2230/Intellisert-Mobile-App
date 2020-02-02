package com.example.intellisert_mobile_app.views;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intellisert_mobile_app.R;
import com.example.intellisert_mobile_app.controllers.BluetoothPairController;

import static android.util.Log.INFO;

public class BluetoothPairActivity extends AppCompatActivity implements BaseView {

    private BluetoothPairController controller;
    private LinearLayout deviceList;
    private TextView deviceListHeader;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_pair_activity);
        init();
        bindActions();

        controller.startBluetooth();
    }

    @Override
    public void init() {
        controller = new BluetoothPairController(this);

        // bluetooth device list
        deviceList = findViewById(R.id.bluetooth_device_list);
        deviceList.setVisibility(View.INVISIBLE);
        deviceList.setEnabled(false);
        deviceListHeader = findViewById(R.id.bluetooth_pair_list_header);
        deviceListHeader.setVisibility(View.INVISIBLE);
        scrollView = findViewById(R.id.bluetooth_device_scroll);
        scrollView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void bindActions() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // dispatch controller actions based on intents fired from the controller
        switch(requestCode){
            case BluetoothPairController.REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    controller.discoverPairedDevices();
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
        deviceListHeader.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
    }

    /**
     * Adds newly discovered bluetooth device to view.
     * @param name - name of the bluetooth device.
     */
    public void addDevice(String name){
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        View btView = inflater.inflate(R.layout.bluetooth_device, deviceList, false);
        TextView btViewText = btView.findViewById(R.id.bluetooth_device_name);
        btViewText.setText(name);

        // when the bluetooth device is clicked in the listview, change its color for feedback and then
        // dispatch action accordingly to controller
        btView.setOnClickListener(v -> {
            Log.println(INFO, "bt_device_clicked", "bt device '" + name + "' clicked");
        });


        deviceList.addView(btView);
    }
}
