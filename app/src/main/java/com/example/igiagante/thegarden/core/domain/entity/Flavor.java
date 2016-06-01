package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ignacio Giagante, on 19/4/16.
 */
public class Flavor implements Parcelable {

    public Flavor() {
        this.selected = false;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("selected")
    private Boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeInt(selected ? 1 : 0);
    }

    public static final Parcelable.Creator<Flavor> CREATOR = new Parcelable.Creator<Flavor>() {
        public Flavor createFromParcel(Parcel in) {
            return new Flavor(in);
        }

        public Flavor[] newArray(int size) {
            return new Flavor[size];
        }
    };

    private Flavor(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        selected = in.readInt() == 1;
    }
}
