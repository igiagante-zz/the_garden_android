package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ignacio Giagante, on 15/4/16.
 */
public class Garden implements Parcelable {

    public Garden() {}

    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("name")
    private String name;

    @SerializedName("startDate")
    private Date startDate;

    @SerializedName("endDate")
    private Date endDate;

    @SerializedName("plants")
    private List<Plant> plants = new ArrayList<>();

    @SerializedName("irrigations")
    private List<Irrigation> irrigations = new ArrayList<>();

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }

    public List<Irrigation> getIrrigations() {
        return irrigations;
    }

    public void setIrrigations(List<Irrigation> irrigations) {
        this.irrigations = irrigations;
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
        dest.writeList(plants);
        dest.writeList(irrigations);
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
        userId   = in.readString();
        name = in.readString();
        in.readList(plants, this.getClass().getClassLoader());
        in.readList(irrigations, this.getClass().getClassLoader());
    }
}
