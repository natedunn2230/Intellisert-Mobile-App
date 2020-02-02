package com.example.intellisert_mobile_app.models;

import java.util.ArrayList;

/**
 * Store that holds each bluetooth device.
 */
public class BluetoothDevices {

    private ArrayList<BluetoothDevice> devices;

    /**
     * Adds a bluetooth device to the store.
     * @param device - bluetooth device to be added.
     */
    public void addDevice(BluetoothDevice device){
        devices.add(device);
    }

    /**
     * Gets all bluetooth devices in the store.
     * @return - all bluetooth devices in the store
     */
    public ArrayList<BluetoothDevice> getDevices(){
        return devices;
    }
}


/**
 * Holds each instance of a bluetooth device.
 */
class BluetoothDevice {

    private String name;
    private String address;

    public BluetoothDevice(String name, String address){
        this.name = name;
        this.address = address;
    }

    /**
     * Gets the game of bluetooth name.
     * @return - Name of bluetooth device.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the address of bluetooth device.
     * @return - MAC address of bluetooth device.
     */
    public String getAddress() {
        return address;
    }

}
