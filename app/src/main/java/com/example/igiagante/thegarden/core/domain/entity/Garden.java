package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ignacio Giagante, on 15/4/16.
 */
public class Garden implements Parcelable {

    public Garden() {}

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    public Garden(String id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Garden> CREATOR = new Parcelable.Creator<Garden>() {
        public Garden createFromParcel(Parcel in) {
            return new Garden(in);
        }

        public Garden[] newArray(int size) {
            return new Garden[size];
        }
    };

    private Garden(Parcel in) {
        id = in.readString();
        name = in.readString();
    }
}
