package com.example.intellisert_mobile_app.models;

/**
 * Holds state of light on Intellisert
 */
public class Light {

    private String power;

    /**
     * Gets the power state
     * @return - state of power (on | off)
     */
    public String getPower() {
        return power;
    }

    /**
     * Set the power state
     * @param power - power state (on | off)
     */
    public void setPower(String power) {
        this.power = power;
    }
}
