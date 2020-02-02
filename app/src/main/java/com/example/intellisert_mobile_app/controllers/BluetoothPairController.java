package com.example.intellisert_mobile_app.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;

import com.example.intellisert_mobile_app.models.BluetoothDev;
import com.example.intellisert_mobile_app.models.BluetoothDevs;
import com.example.intellisert_mobile_app.views.BluetoothPairActivity;

import java.util.Set;

import static android.util.Log.INFO;

public class BluetoothPairController implements Controllable {

    private BluetoothPairActivity view;
    private BluetoothDevs btDevices;
    private BluetoothAdapter btAdapter;

    public static final int REQUEST_ENABLE_BT = 1;

    public BluetoothPairController(BluetoothPairActivity view){
        this.view = view;
        this.btAdapter = BluetoothAdapter.getDefaultAdapter();
        btDevices = new BluetoothDevs();
    }

    /**
     * Starts bluetooth pairing process.
     */
    public void startBluetooth() {
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            view.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            discoverPairedDevices();
        }

    }

    /**
     * Discovers bluetooth devices that have not been paired
     */
    public void discoverPairedDevices() {
        Set<BluetoothDevice>pairedDevices = btAdapter.getBondedDevices();

        for(BluetoothDevice bd: pairedDevices){
            BluetoothDev newDevice = new BluetoothDev(bd.getName(), bd.getAddress());
            Log.println(INFO, "paired_device_found", bd.getName());
            btDevices.addDevice(new BluetoothDev(bd.getName(), bd.getAddress()));
            view.addDevice(newDevice.getName());
        }
        view.showList();
    }


    @Override
    public void changeView(Class toView) {

    }
}
