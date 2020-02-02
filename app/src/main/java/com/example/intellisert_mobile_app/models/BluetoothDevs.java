package com.example.intellisert_mobile_app.models;

import java.util.ArrayList;

/**
 * Store that holds each bluetooth device.
 */
public class BluetoothDevs {

    private ArrayList<BluetoothDev> devices;

    public BluetoothDevs(){
        devices = new ArrayList<>();
    }

    /**
     * Adds a bluetooth device to the store.
     * @param device - bluetooth device to be added.
     */
    public void addDevice(BluetoothDev device){
        devices.add(device);
    }

    /**
     * Gets all bluetooth devices in the store.
     * @return - all bluetooth devices in the store
     */
    public ArrayList<BluetoothDev> getDevices(){
        return devices;
    }
}
