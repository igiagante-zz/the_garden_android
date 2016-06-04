package com.example.igiagante.thegarden.creation.plants.presentation.dataHolders;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;

/**
 * @author Ignacio Giagante, on 1/6/16.
 */
public class FlavorHolder extends DataHolder<Flavor> {

    public FlavorHolder() {
    }

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImageUrl() {
        return getModel().getImageUrl();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(getModel(), 0);
        dest.writeInt(selected ? 1 : 0);
    }

    public static final Parcelable.Creator<FlavorHolder> CREATOR = new Parcelable.Creator<FlavorHolder>() {
        public FlavorHolder createFromParcel(Parcel in) {
            return new FlavorHolder(in);
        }

        public FlavorHolder[] newArray(int size) {
            return new FlavorHolder[size];
        }
    };

    private FlavorHolder(Parcel in) {
        model = in.readParcelable(this.getClass().getClassLoader());
        selected = in.readInt() == 1;
    }
}
