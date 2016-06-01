package com.example.igiagante.thegarden.core.presentation.mvp;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author Ignacio Giagante, on 4/5/16.
 */
public abstract class AbstractPresenter<T extends IView> {

    protected WeakReference<T> view;

    @Nullable
    public T getView() {
        return view.get();
    }

    public void setView(WeakReference<T> view) {
        this.view = view;
    }
}
