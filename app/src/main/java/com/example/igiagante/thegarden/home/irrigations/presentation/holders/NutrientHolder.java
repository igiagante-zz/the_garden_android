package com.example.igiagante.thegarden.home.irrigations.presentation.holders;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.DataHolder;

/**
 * @author Ignacio Giagante, on 26/7/16.
 */
public class NutrientHolder extends DataHolder<Nutrient> {

    private boolean selected = false;

    public NutrientHolder() {
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

    public static final Parcelable.Creator<NutrientHolder> CREATOR = new Parcelable.Creator<NutrientHolder>() {
        public NutrientHolder createFromParcel(Parcel in) {
            return new NutrientHolder(in);
        }

        public NutrientHolder[] newArray(int size) {
            return new NutrientHolder[size];
        }
    };

    private NutrientHolder(Parcel in) {
        model = in.readParcelable(this.getClass().getClassLoader());
        selected = in.readInt() == 1;
    }

    public String getName(){
        return getModel().getName();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setQuantity(float quantity){
        getModel().setQuantityUsed(quantity);
    }
}
