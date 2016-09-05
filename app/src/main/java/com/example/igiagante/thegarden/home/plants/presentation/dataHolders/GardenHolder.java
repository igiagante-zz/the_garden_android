package com.example.igiagante.thegarden.home.plants.presentation.dataHolders;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.DataHolder;

/**
 * @author Ignacio Giagante, on 11/7/16.
 */
public class GardenHolder extends DataHolder<Garden> {

    private int position;
    private boolean isSelected;

    public GardenHolder() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getGardenId() {
        return getModel().getId();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getModel(), 0);
        dest.writeInt(position);
    }

    public static final Parcelable.Creator<GardenHolder> CREATOR = new Parcelable.Creator<GardenHolder>() {
        public GardenHolder createFromParcel(Parcel in) {
            return new GardenHolder(in);
        }

        public GardenHolder[] newArray(int size) {
            return new GardenHolder[size];
        }
    };

    private GardenHolder(Parcel in) {
        model = in.readParcelable(this.getClass().getClassLoader());
        position = in.readInt();
    }
}
