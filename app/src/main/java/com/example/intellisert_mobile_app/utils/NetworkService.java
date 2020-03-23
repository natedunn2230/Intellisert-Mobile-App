package com.example.intellisert_mobile_app.utils;


import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkService {

    public final String NETWORK_SERVICE = "NETWORK_SERVICE";
    public  final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static NetworkService instance = null;

    private String deviceAddress = null;
    private OkHttpClient client;

    private NetworkService() {
        client = new OkHttpClient();
    }

    public static NetworkService getInstance() {
        if(instance == null)
            instance = new NetworkService();

        return instance;
    }

    public void setDeviceAddress(String address){
        Log.d(NETWORK_SERVICE, "device ip: " + address + " has been set");
        deviceAddress = address;
    }

    public String post(String json, String endpoint) throws IOException {
        Log.d(NETWORK_SERVICE, String.format("sending %s to endpoint: %s", json, endpoint));
        if(deviceAddress != null) {
            RequestBody body = RequestBody.create(json, JSON);
            Request request = new Request.Builder()
                    .url("http://" + deviceAddress + "/" + endpoint)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String responseString = response.body().string();

            Log.d(NETWORK_SERVICE, String.format("received response: %s", responseString));
            return responseString;
        }

        return "no device ip set";
    }

    public String get(String endpoint) throws IOException {
        Log.d(NETWORK_SERVICE, String.format("sending request to endpoint: %s", endpoint));

        if(deviceAddress != null) {

            Request request = new Request.Builder()
                    .url("http://" + deviceAddress + "/" + endpoint)
                    .build();

            Response response = client.newCall(request).execute();
            String responseString = response.body().string();

            Log.d(NETWORK_SERVICE, String.format("received response: %s", responseString));
            return responseString;
        }

        return "no device ip set";
    }
}
