package com.example.intellisert_mobile_app.stuff;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Holds methods to be implemented in each view.
 */
public abstract class BaseView {
    protected View rootView;
    protected Context context;

    BaseView(ViewGroup viewRef, Context contextRef){
        rootView = viewRef;
        context = contextRef;
    }

    /**
     * Returns the root view of the View
     * @return View: Reference to the root view
     */
    abstract View getRootView();

    /**
     * Initializes references to the view components
     */
    abstract void init();
}
