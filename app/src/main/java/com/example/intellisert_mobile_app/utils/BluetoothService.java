package com.example.intellisert_mobile_app.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.example.intellisert_mobile_app.models.BluetoothDevices;

import java.util.List;
import java.util.Set;

import static android.util.Log.INFO;

/**
 * Utility used for establishing a bluetooth connection between the mobile device and a bluetooth
 * device.
 */
public class BluetoothService {

    private BluetoothAdapter btAdapter;
    private BluetoothDevices btDevices;

    public BluetoothService() {
        btDevices = new BluetoothDevices();
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * Returns boolean showing if the bluetooth adapter is enabled or not.
     * @return - boolean depicting whether the bluetooth adapter is enabled.
     */
    public boolean isEnabled(){
        return btAdapter.isEnabled();
    }

    /**
     * Bluetooth Adapter searches the phone's bluetooth configuration for currently
     * paired devices and stores them in bluetooth devices.
     */
    public void findPairedDevices(){
        if(btAdapter.isEnabled()){
            Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
            for(BluetoothDevice bd: pairedDevices){
                Log.println(INFO, "paired_device_found", bd.getName());
                btDevices.addDevice(bd);
            }
        }
    }

    /**
     * Returns the paired bluetooth devices.
     * @return - devices that are paired to the mobile device.
     */
    public List<BluetoothDevice> getDevices(){
        return btDevices.getDevices();
    }
}
