package com.example.intellisert_mobile_app.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.intellisert_mobile_app.models.BluetoothDevices;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
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
    private boolean attemptingConnection = false;

    private final String BT_SERVICE_TAG = "BT_SERVICE";

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


    /**
     * Connects to the specified bluetooth device and sends data to it.
     * @param name - name of bluetooth device to connect to.
     * @param data - data to send to the bluetooth device.
     */
    public void connectToDevice(String name, String data){
        if(!attemptingConnection) {
            attemptingConnection = true;
            selectedDevice = btDevices.getDevice(name);
            Thread thread = new Thread(new WorkerThread(data));
            thread.start();
        }
    }

    /**
     * Opens a bluetooth connection to the selected bluetooth device and sends a message.
     * @param msg - message to be sent to bluetooth device.
     * @return - successful connection and write to bluetooth device.
     */
    private boolean sendMsg(String msg){
        UUID uuid = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
        try{
            btSocket = selectedDevice.createRfcommSocketToServiceRecord(uuid);

            if(!btSocket.isConnected()){
                btSocket.connect();
            }

            OutputStream btOutputStream = btSocket.getOutputStream();
            btOutputStream.write(msg.getBytes());
            return true;

        } catch(IOException e){
            Log.e(BT_SERVICE_TAG, "error creating and establishing connection with socket: " + e);
            return false;
        }
    }

    /**
     * Class is responsible for running an asynchronous connection with the desired bluetooth device.
     */
    final class WorkerThread implements Runnable {
        private String msg;

        WorkerThread(String msg){
            this.msg = msg;
        }

        @Override
        public void run() {
            // if message is sent successfully
            if (sendMsg(msg)) {
                while (!Thread.currentThread().isInterrupted()) {
                    int bytesAvailable;
                    boolean workDone = false;
                    // read from input stream from the bluetooth socket
                    try {
                        final InputStream btInputStream;
                        btInputStream = btSocket.getInputStream();
                        bytesAvailable = btInputStream.available();
                        String data;

                        // if there is data to read
                        if (bytesAvailable > 0) {
                            int bytesLeft = bytesAvailable;
                            final int bufferSize = 1024;

                            Log.d(BT_SERVICE_TAG, "bytes available");
                            final char[] buffer = new char[bufferSize];
                            final StringBuilder sBuilder = new StringBuilder();
                            Reader in = new InputStreamReader(btInputStream, StandardCharsets.UTF_8);

                            int chunk;

                            // read message from stream, in chunks
                            while (bytesLeft > 0) {
                                chunk = in.read(buffer, 0, buffer.length);
                                sBuilder.append(buffer, 0, chunk);
                                bytesLeft -= bufferSize;
                            }
                            data = sBuilder.toString();

                            Log.d(BT_SERVICE_TAG, "data received: " + data);
                            btSocket.close();
                            break;
                        }

                    } catch (IOException e) {
                        Log.e(BT_SERVICE_TAG, "error reading from socket: " + e);
                    }

                    Log.d(BT_SERVICE_TAG, "in thread loop");
                }

                Log.d(BT_SERVICE_TAG, "thread terminated");
            }

            attemptingConnection = false;
        }
    }

}

