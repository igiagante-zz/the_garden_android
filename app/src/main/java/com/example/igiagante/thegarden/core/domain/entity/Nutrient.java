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

    public Nutrient() {
    }

    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("name")
    private String name;

    @SerializedName("ph")
    private float ph;

    @SerializedName("npk")
    private String npk;

    @SerializedName("description")
    private String description;

    @SerializedName("quantityUsed")
    private float quantityUsed;

    @SerializedName("images")
    private List<Image> images = new ArrayList<>();

    @SerializedName("resourcesIds")
    private List<String> resourcesIds = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public float getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(float quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<String> getResourcesIds() {
        return resourcesIds;
    }

    public void setResourcesIds(List<String> resourcesIds) {
        this.resourcesIds = resourcesIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeFloat(ph);
        dest.writeString(npk);
        dest.writeString(description);
        dest.writeFloat(quantityUsed);
        dest.writeList(images);
        dest.writeList(resourcesIds);
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
        userId = in.readString();
        name = in.readString();
        ph = in.readFloat();
        npk = in.readString();
        description = in.readString();
        quantityUsed = in.readFloat();
        in.readList(images, this.getClass().getClassLoader());
        in.readList(resourcesIds, this.getClass().getClassLoader());
    }
}
