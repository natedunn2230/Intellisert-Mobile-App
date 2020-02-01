package com.example.intellisert_mobile_app.controllers;

import android.app.Activity;

import com.example.intellisert_mobile_app.views.BaseView;

/**
 * Holds methods to be implemented in each controller(Activity);
 */
public interface Controllable {

    /**
     * Updates the view assigned to the controller.
     */
    void updateView();


    /**
     * Changes to another view/activity of the application.
     * @param toView - class of view to transition to.
     */
    void changeView(Class toView);
}
