package com.example.intellisert_mobile_app.views;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intellisert_mobile_app.R;
import com.example.intellisert_mobile_app.controllers.BluetoothPairController;
import com.example.intellisert_mobile_app.views.BluetoothPairCredentialDialog.BluetoothPairCredentialListener;

public class BluetoothPairActivity extends AppCompatActivity implements BaseView, BluetoothPairCredentialListener {

    private BluetoothPairController controller;
    private LinearLayout deviceList;
    private TextView deviceListHeader;
    private ScrollView scrollView;
    private ProgressBar progressBar;

    private final String BLUETOOTH_PAIR_ACTIVITY = "BLUETOOTH_PAIR_ACTIVITY";

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

        progressBar = findViewById(R.id.bluetooth_pair_progress);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void bindActions() { }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // dispatch controller actions based on intents fired from the controller
        switch(requestCode) {
            case BluetoothPairController.REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) {
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
     * Display a toast.
     * @param msg - Message to be displayed
     */
    public void showToast(String msg) {
        Context currentContext = this;

        // Toast must be created on UI thread.
        Runnable toastAction = () -> { Toast.makeText(currentContext, msg, Toast.LENGTH_LONG).show(); };

        runOnUiThread(toastAction);
    }

    /**
     * Set the visibility of the progress circle.
     * @param visible - boolean depicting whether or not the progress circle should be visible.
     */
    public void progressVisible(boolean visible){
        if(visible)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Adds newly discovered bluetooth device to view.
     * @param name - name of the bluetooth device.
     */
    public void addDevice(String name){
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        View btView = inflater.inflate(R.layout.device_token, deviceList, false);
        Button btViewBtn = btView.findViewById(R.id.bluetooth_device_name);
        btViewBtn.setText(name);

        Drawable btnBackground = getDrawable(R.drawable.rounded_primary_dark);
        btViewBtn.setBackground(btnBackground);

        // quick connect for user, by issuing a test connection on the device
        btViewBtn.setOnClickListener(v -> {
            Log.d(BLUETOOTH_PAIR_ACTIVITY, "bt device '" + name + "' clicked");
            controller.setSelectedDevice(name);
            controller.startConnection("\"payload\": {}", true);
        });

        // configuration of device's network
        btViewBtn.setOnLongClickListener(v -> {
            Log.d(BLUETOOTH_PAIR_ACTIVITY, "bt device '" + name + "'long clicked");
            controller.setSelectedDevice(name);
            openCredentialDialog();
            return true;
        });


        deviceList.addView(btView);
    }

    /**
     * Disables UI interactivity
     */
    public void disableUI() {
        Runnable disableAction = () -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        };

        runOnUiThread(disableAction);
    }

    /**
     * Enables UI interactivity
     */
    public void enableUI() {
        Runnable enableAction = () -> {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        };

        runOnUiThread(enableAction);
    }

    @Override
    public void passCredentials(String networkName, String password) {
        Log.d(BLUETOOTH_PAIR_ACTIVITY, "received network: " + networkName + " and its password");

        String message = String.format("\"payload\": {\"ssid\":\"%s\", \"key\": \"%s\"}", networkName, password);

        // use the information passed back from the network credential dialog to attempt connection with device
        controller.startConnection(message, false);
    }

    /**
     * Opens the dialog fragment for user to enter network credentials.
     */
    public void openCredentialDialog(){
        BluetoothPairCredentialDialog credentialDialog = new BluetoothPairCredentialDialog();
        credentialDialog.show(getSupportFragmentManager(), "credential dialog");
    }
}
