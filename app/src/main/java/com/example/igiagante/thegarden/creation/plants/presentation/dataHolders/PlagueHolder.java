package com.example.igiagante.thegarden.creation.plants.presentation.dataHolders;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.network.Settings;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class PlagueHolder extends DataHolder<Plague> {

    private boolean selected = false;

    public PlagueHolder() {
    }

    /**
     * Get the image's path without domain
     * @return image's path
     */
    public String getImagePath() {
        String url = getModel().getImageUrl();
        if(!TextUtils.isEmpty(url) && getModel().getImageUrl().contains("http")) {
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

    public static final Parcelable.Creator<PlagueHolder> CREATOR = new Parcelable.Creator<PlagueHolder>() {
        public PlagueHolder createFromParcel(Parcel in) {
            return new PlagueHolder(in);
        }

        public PlagueHolder[] newArray(int size) {
            return new PlagueHolder[size];
        }
    };

    private PlagueHolder(Parcel in) {
        model = in.readParcelable(this.getClass().getClassLoader());
        selected = in.readInt() == 1;
    }

    public String getPlagueName() {
        return getModel().getName();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
