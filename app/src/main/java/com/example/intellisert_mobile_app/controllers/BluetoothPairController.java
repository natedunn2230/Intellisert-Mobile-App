package com.example.intellisert_mobile_app.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;

import com.example.intellisert_mobile_app.utils.BluetoothService;
import com.example.intellisert_mobile_app.views.BluetoothPairActivity;

import java.util.Set;

import static android.util.Log.INFO;

public class BluetoothPairController implements Controllable {

    private BluetoothPairActivity view;
    private BluetoothService btService;

    public static final int REQUEST_ENABLE_BT = 1;
    public final String BT_PAIR_CONTROLLER = "BT_PAIR_CONTROLLER";

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

    public void startConnection(String name, String data) {
        btService.connectToDevice(name, data);
    }


    @Override
    public void changeView(Class toView) {

    }
}
