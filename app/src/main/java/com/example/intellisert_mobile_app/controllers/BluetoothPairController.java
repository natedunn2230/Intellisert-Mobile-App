package com.example.intellisert_mobile_app.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;

import com.example.intellisert_mobile_app.utils.BluetoothService;
import com.example.intellisert_mobile_app.utils.NetworkService;
import com.example.intellisert_mobile_app.views.ActionActivity;
import com.example.intellisert_mobile_app.views.BluetoothPairActivity;

import java.io.IOException;

public class BluetoothPairController implements Controllable {

    private BluetoothPairActivity view;
    private BluetoothService btService;
    private NetworkService networkService;

    public static final int REQUEST_ENABLE_BT = 1;

    private final String BT_PAIR_CONTROLLER = "BT_PAIR_CONTROLLER";

    public BluetoothPairController(BluetoothPairActivity view){
        this.view = view;
        this.btService = new BluetoothService();
    }

    /**
     * Starts bluetooth pairing process.
     */
    public void startBluetooth() {
        if (!btService.isEnabled() && btService.isSupported()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            view.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else if(btService.isSupported()) {
            discoverPairedDevices();
        } else {
            Log.e(BT_PAIR_CONTROLLER, "Unable to start bluetooth");
        }
    }

    /**
     * Discovers bluetooth devices that have not been paired
     */
    public void discoverPairedDevices() {
        if(btService.isSupported()) {
            btService.findPairedDevices();

            for (BluetoothDevice device : btService.getDevices()) {
                view.addDevice(device.getName());
            }
        } else {
            Log.e(BT_PAIR_CONTROLLER, "Unable to discover devices");
        }
        view.showList();
    }

    /**
     * Start connection with the Bluetooth device.
     * @param data - data to be passed to Bluetooth Device.
     * @param test - test for Bluetooth Device having existing network connectivity
     */
    public void startConnection(String data, boolean test) {
        String msgType = test ? "test" : "configure";
        String msg = String.format("{\"type\": \"%s\", %s}", msgType, data);

        btService.connectToDevice(msg, resultSetter, deviceIPSetter);
        view.progressVisible(true);
        view.disableUI();
    }

    /**
     * Callback object to receive result from Bluetooth Thread
     */
    private ThreadResultSetter resultSetter = new ThreadResultSetter<Boolean>() {
        @Override
        public void setResult(Boolean result) {
            view.progressVisible(false);
            Log.d(BT_PAIR_CONTROLLER, "Result from thread result setter: " + result);

            if(result) {
                view.showToast("Device connected to network");
                changeView(ActionActivity.class);
            } else {
                view.showToast("Could not connect device to network");
            }

            view.enableUI();
        }
    };


    /**
     * Callback object to receive device IP from bluetooth service
     */
    private ThreadResultSetter deviceIPSetter = new ThreadResultSetter<String>() {
        @Override
        public void setResult(String ip) {

            if (ip != null) {
                Log.d(BT_PAIR_CONTROLLER, "Result from thread Device IP setter: " + ip);
                NetworkService nwService = NetworkService.getInstance();
                nwService.setDeviceAddress(ip);

            } else {
                Log.e(BT_PAIR_CONTROLLER, "Device IP is null");
            }
        }
    };

    /**
     * Set Bluetooth device to be connected to.
     * @param name - name of device to be connected to.
     */
    public void setSelectedDevice(String name) {
        btService.setSelectedDevice(name);
    }

    @Override
    public void changeView(Class toView) {
        Intent intent = new Intent(view, toView);
        view.startActivity(intent);
    }
}
