package com.example.igiagante.thegarden.creation.plants.presentation.dataHolders;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;

/**
 * @author Ignacio Giagante, on 2/6/16.
 */
public class AttributeHolder extends DataHolder<Attribute> {

    private boolean selected = false;

    public AttributeHolder() {
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

    public static final Parcelable.Creator<AttributeHolder> CREATOR = new Parcelable.Creator<AttributeHolder>() {
        public AttributeHolder createFromParcel(Parcel in) {
            return new AttributeHolder(in);
        }

        public AttributeHolder[] newArray(int size) {
            return new AttributeHolder[size];
        }
    };

    private AttributeHolder(Parcel in) {
        model = in.readParcelable(this.getClass().getClassLoader());
        selected = in.readInt() == 1;
    }

    public void setTagName(String tagName) {
        getModel().setName(tagName);
    }

    public String getTagName(){
        return getModel().getName();
    }

    public void setPercentage(int percentage) {
        getModel().setPercentage(percentage);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getType() {
        return getModel().getType();
    }

}
