package com.example.intellisert_mobile_app.views;

/**
 * Holds methods to be implemented in each view.
 */
public interface BaseView {
    /**
     * Initializes references to the view components that will be programmatically changed.
     */
    void init();

    /**
     * Binds actions to appropriate UI components.
     */
    void bindActions();
}
