package com.example.igiagante.thegarden.creation.plants.presentation.holders;

import android.os.Parcelable;

/**
 * Wrapper class used to detach the view from the model.
 *
 * @author Ignacio Giagante, on 1/6/16.
 */
public abstract class DataHolder<T> implements Parcelable {

    protected T model;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
