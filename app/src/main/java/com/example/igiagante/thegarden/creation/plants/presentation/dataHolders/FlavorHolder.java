package com.example.igiagante.thegarden.creation.plants.presentation.dataHolders;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.presentation.DataHolder;
import com.example.igiagante.thegarden.core.repository.network.Settings;

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

    /**
     * Get Image Url resource
     *
     * @return image url
     */
    public String getImageUrl() {
        return getModel().getImageUrl();
    }

    public String getName() {
        return getModel().getName();
    }

    /**
     * Get the image's path without domain
     *
     * @return image's path
     */
    public String getImagePath() {
        String url = getModel().getImageUrl();
        if (!TextUtils.isEmpty(url) && getModel().getImageUrl().contains("http")) {
            url = url.replace(Settings.DOMAIN, "");
            return url;
        }
        return url;
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
