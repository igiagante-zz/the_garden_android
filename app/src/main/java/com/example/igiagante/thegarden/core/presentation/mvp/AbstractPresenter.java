package com.example.igiagante.thegarden.core.presentation.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by igiagante on 4/5/16.
 */
public abstract class AbstractPresenter<T extends IView> {

    protected WeakReference<T> view;

    public WeakReference<T> getView() {
        return view;
    }

    public void setView(WeakReference<T> view) {
        this.view = view;
    }
}
