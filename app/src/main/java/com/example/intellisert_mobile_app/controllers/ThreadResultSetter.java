package com.example.intellisert_mobile_app.controllers;

/**
 * Used as a callback object to get a result from a thread
 * @param <T> - Generic type for thread result value
 */
public interface ThreadResultSetter<T> {

    /**
     * Sets the result of the thread.
     * @param result - result of thread.
     */
    void setResult(T result);
}
