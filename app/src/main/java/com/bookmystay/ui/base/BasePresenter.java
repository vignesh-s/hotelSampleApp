package com.bookmystay.ui.base;

public class BasePresenter<T> {

    private T mViewer;

    public BasePresenter(T mViewer) {
        this.mViewer = mViewer;
    }

    protected T getViewer() {
        return mViewer;
    }
}
