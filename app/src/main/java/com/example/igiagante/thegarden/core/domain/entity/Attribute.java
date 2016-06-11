package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ignacio Giagante, on 11/5/16.
 */
public class Attribute implements Parcelable {

    public Attribute() {}

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("percentage")
    private int percentage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeInt(percentage);
    }

    public static final Parcelable.Creator<Attribute> CREATOR = new Parcelable.Creator<Attribute>() {
        public Attribute createFromParcel(Parcel in) {
            return new Attribute(in);
        }

        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };

    private Attribute(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readString();
        percentage = in.readInt();
    }
}
