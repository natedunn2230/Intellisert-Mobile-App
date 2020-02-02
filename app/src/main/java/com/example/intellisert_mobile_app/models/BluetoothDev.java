package com.example.intellisert_mobile_app.models;

/**
 * Holds each instance of a bluetooth device.
 */
public class BluetoothDev {

    private String name;
    private String address;

    public BluetoothDev(String name, String address){
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
