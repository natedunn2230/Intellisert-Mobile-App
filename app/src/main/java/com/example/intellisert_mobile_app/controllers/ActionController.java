package com.example.intellisert_mobile_app.controllers;

import android.util.Log;

import com.example.intellisert_mobile_app.models.Light;
import com.example.intellisert_mobile_app.utils.NetworkService;
import com.example.intellisert_mobile_app.views.ActionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ActionController implements Controllable {

    private ActionActivity view;
    private NetworkService networkService;
    private Light light;
    private final String ACTION_CONTROLLER = "ACTION_CONTROLLER";

    public ActionController(ActionActivity view){
        this.view = view;
        networkService = NetworkService.getInstance();
        this.light = new Light();
    }

    /**
     * Makes an HTTP request to the Intellisert Server to obtain light state
     */
    public void fetchLightState(){
        try {
            String resp = networkService.get("/light-state");
            Log.d(ACTION_CONTROLLER, String.format("Got Light State: %s ", resp));

            JSONObject data = new JSONObject(resp);
            String lightPower = data.getString("power");
            view.setLightButtonState(lightPower);
            light.setPower(lightPower);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e(ACTION_CONTROLLER, "Could not fetch light state.");
        }
    }

    /**
     * Makes an HTTP request to the Intellisert Server to either power on or off the light based off of the
     * light state
     */
    public void setLightPower() {
        String requestString = String.format("{\"power\": \"%s\"}", light.getPower().equals("on")? "off": "on");
        try {
            String responseString = networkService.post(requestString, "/light/power");
            JSONObject response = new JSONObject(responseString);
            String msg = response.getString("power");
            if(msg.equals("on") || msg.equals("off")) {
              view.setLightButtonState(msg);
              light.setPower(msg);
            } else {
                Log.e(ACTION_CONTROLLER, "Unable to set light power: " + msg);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e(ACTION_CONTROLLER, "Could not set light power.");
        }
    }

    @Override
    public void changeView(Class toView) {

    }
}
