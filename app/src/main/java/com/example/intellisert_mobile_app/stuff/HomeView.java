package com.example.intellisert_mobile_app.stuff;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.intellisert_mobile_app.R;

public class HomeView extends BaseView {

    private Button startButton, aboutButton;

    public HomeView(ViewGroup viewRef, Context contextRef){
        super(viewRef, contextRef);
        init();
    }

    @Override
    public View getRootView() {
         return rootView;
    }

    @Override
    void init() {
        startButton = rootView.findViewById(R.id.button_start);
        aboutButton  = rootView.findViewById(R.id.button_about);
    }
}
