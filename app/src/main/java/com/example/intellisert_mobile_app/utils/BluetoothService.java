package com.example.intellisert_mobile_app.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.intellisert_mobile_app.models.BluetoothDevices;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.util.Log.INFO;

/**
 * Utility used for establishing a bluetooth connection between the mobile device and a bluetooth
 * device.
 */
public class BluetoothService {

    private BluetoothAdapter btAdapter;
    private BluetoothDevices btDevices;

    private BluetoothSocket btSocket = null;
    private BluetoothDevice selectedDevice = null;

    final byte delimiter = 33;
    int readBufferPosition = 0;

    private final String BT_SERVICE_TAG = "BT_SERVICE_TAG";

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

    public void connectToDevice(String name){
        selectedDevice = btDevices.getDevice(name);
        Thread thread = new Thread(new WorkerThread("data!"));
        thread.start();
    }


    private void sendMsg(String msg){
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
        try{
            btSocket = selectedDevice.createRfcommSocketToServiceRecord(uuid);

            if(!btSocket.isConnected()){
                btSocket.connect();
            }

            OutputStream btOutputStream = btSocket.getOutputStream();
            btOutputStream.write(msg.getBytes());

        } catch(IOException e){
            Log.e(BT_SERVICE_TAG, "error creating and establishing connection with socket: " + e);
        }
    }


    final class WorkerThread implements Runnable {
        private String msg;

        public WorkerThread(String msg){
            this.msg = msg;
        }

        @Override
        public void run() {
            sendMsg(msg);

            while(!Thread.currentThread().isInterrupted()){
                int bytesAvailable;
                boolean workDone = false;

                try {
                    final InputStream btInputStream;
                    btInputStream = btSocket.getInputStream();
                    bytesAvailable = btInputStream.available();

                    if(bytesAvailable > 0){
                        byte[] packetBytes = new byte[bytesAvailable];
                        Log.d(BT_SERVICE_TAG,"bytes available");
                        byte[] readBuffer = new byte[1024];
                        btInputStream.read(packetBytes);

                        for(int i=0;i<bytesAvailable;i++) {
                            byte b = packetBytes[i];
                            if(b == delimiter){
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                final String data = new String(encodedBytes, "US-ASCII");
                                readBufferPosition = 0;
                                workDone = true;
                                Log.d(BT_SERVICE_TAG, data);
                                break;
                            }
                            else
                            {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }

                        if (workDone){

                            btSocket.close();
                            break;
                        }
                    }

                } catch(IOException e) {
                    Log.e(BT_SERVICE_TAG, "error reading from socket: " + e);
                }
            }
        }
    }

}

