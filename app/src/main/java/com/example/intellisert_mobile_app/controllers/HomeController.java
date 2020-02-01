package com.example.intellisert_mobile_app.controllers;

import android.content.Intent;

import com.example.intellisert_mobile_app.views.HomeActivity;

public class HomeController implements Controllable {

    private HomeActivity view;

    public HomeController(HomeActivity view){
        this.view = view;
    }

    @Override
    public void updateView() {

    }

    @Override
    public void changeView(Class toView) {
        Intent intent = new Intent(view, toView);
        view.startActivity(intent);
    }
}
