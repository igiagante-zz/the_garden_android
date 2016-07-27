package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class Dose implements Parcelable {

    public Dose() {
    }

    @SerializedName("id")
    private String id;

    @SerializedName("water")
    private float water;

    @SerializedName("phDose")
    private float phDose;

    @SerializedName("ec")
    private float ec;

    @SerializedName("ph")
    private float ph;

    @SerializedName("nutrients")
    private List<Nutrient> nutrients = new ArrayList<>();

    @SerializedName("nutrientsIds")
    private List<String> nutrientsIds = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public float getPhDose() {
        return phDose;
    }

    public void setPhDose(float phDose) {
        this.phDose = phDose;
    }

    public float getEc() {
        return ec;
    }

    public void setEc(float ec) {
        this.ec = ec;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public List<String> getNutrientsIds() {
        return nutrientsIds;
    }

    public void setNutrientsIds(List<String> nutrientsIds) {
        this.nutrientsIds = nutrientsIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeFloat(water);
        dest.writeFloat(phDose);
        dest.writeFloat(ec);
        dest.writeFloat(ph);
        dest.writeList(nutrients);
        dest.writeList(nutrientsIds);
    }

    public static final Parcelable.Creator<Dose> CREATOR = new Parcelable.Creator<Dose>() {
        public Dose createFromParcel(Parcel in) {
            return new Dose(in);
        }

        public Dose[] newArray(int size) {
            return new Dose[size];
        }
    };

    private Dose(Parcel in) {
        id = in.readString();
        water = in.readFloat();
        phDose = in.readFloat();
        ec = in.readFloat();
        ph = in.readFloat();
        in.readList(nutrients, this.getClass().getClassLoader());
        in.readList(nutrientsIds, this.getClass().getClassLoader());
    }
}
