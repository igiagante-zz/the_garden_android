package com.example.igiagante.thegarden.creation.plants.presentation.holders;

import android.os.Parcel;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;

/**
 * @author Ignacio Giagante, on 2/6/16.
 */
public class AttributeHolder extends DataHolder<Attribute> {

    private static final int delta = 10;
    private boolean selected;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
