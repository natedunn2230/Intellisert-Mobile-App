package com.example.intellisert_mobile_app.models;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

/**
 * Store that holds each bluetooth device.
 */
public class BluetoothDevices {

    private ArrayList<BluetoothDevice> devices;

    public BluetoothDevices(){
        devices = new ArrayList<>();
    }

    /**
     * Adds a bluetooth device to the store.
     * @param device - bluetooth device to be added.
     */
    public void addDevice(BluetoothDevice device){
        devices.add(device);
    }

    /**
     * Gets all bluetooth devices in the store.
     * @return - all bluetooth devices in the store.
     */
    public ArrayList<BluetoothDevice> getDevices(){
        return devices;
    }

    /**
     * Gets a bluetooth device based off its name
     * @param name - name of bluetooth device to get.
     * @return - bluetooth device with name specified.
     */
    public BluetoothDevice getDevice(String name) {
        for(BluetoothDevice device : devices){
            if(device.getName().equals(name))
                return device;
        }

        return null;
    }
}
