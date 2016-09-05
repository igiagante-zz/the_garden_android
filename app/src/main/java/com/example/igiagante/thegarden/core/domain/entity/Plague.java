package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class Plague implements Parcelable {

    public Plague() {
    }

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imageUrl;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
    }

    public static final Parcelable.Creator<Plague> CREATOR = new Parcelable.Creator<Plague>() {
        public Plague createFromParcel(Parcel in) {
            return new Plague(in);
        }

        public Plague[] newArray(int size) {
            return new Plague[size];
        }
    };

    private Plague(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrl = in.readString();
    }
}
