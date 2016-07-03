package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class Nutrient implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("ph")
    private float ph;

    @SerializedName("npk")
    private String npk;

    @SerializedName("name")
    private String description;

    @SerializedName("images")
    private List<Image> images = new ArrayList<>();

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

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public String getNpk() {
        return npk;
    }

    public void setNpk(String npk) {
        this.npk = npk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeFloat(ph);
        dest.writeString(npk);
        dest.writeString(description);
        dest.writeList(images);
    }

    public static final Parcelable.Creator<Nutrient> CREATOR = new Parcelable.Creator<Nutrient>() {
        public Nutrient createFromParcel(Parcel in) {
            return new Nutrient(in);
        }

        public Nutrient[] newArray(int size) {
            return new Nutrient[size];
        }
    };

    private Nutrient(Parcel in) {
        id = in.readString();
        name = in.readString();
        ph = in.readFloat();
        npk = in.readString();
        description = in.readString();
        in.readList(images, this.getClass().getClassLoader());
    }
}
