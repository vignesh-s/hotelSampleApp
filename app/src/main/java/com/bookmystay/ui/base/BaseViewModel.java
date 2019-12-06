package com.bookmystay.ui.base;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {

    private CompositeDisposable mCompositeDisposable;
    private WeakReference<N> mViewer;

    protected BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    protected CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    protected N getViewer() {
        return mViewer.get();
    }

    public void setNavigator(N navigator) {
        this.mViewer = new WeakReference<>(navigator);
    }
}
