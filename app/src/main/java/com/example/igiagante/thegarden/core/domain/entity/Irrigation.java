package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class Irrigation implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("irrigationDate")
    private Date irrigationDate;

    @SerializedName("gardenId")
    private String gardenId;

    @SerializedName("doseId")
    private String doseId;

    @SerializedName("quantity")
    private float quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getIrrigationDate() {
        return irrigationDate;
    }

    public void setIrrigationDate(Date irrigationDate) {
        this.irrigationDate = irrigationDate;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public String getDoseId() {
        return doseId;
    }

    public void setDoseId(String doseId) {
        this.doseId = doseId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeSerializable(irrigationDate);
        dest.writeString(gardenId);
        dest.writeString(doseId);
        dest.writeFloat(quantity);
    }

    public static final Parcelable.Creator<Irrigation> CREATOR = new Parcelable.Creator<Irrigation>() {
        public Irrigation createFromParcel(Parcel in) {
            return new Irrigation(in);
        }

        public Irrigation[] newArray(int size) {
            return new Irrigation[size];
        }
    };

    private Irrigation(Parcel in) {
        id = in.readString();
        irrigationDate = (Date) in.readSerializable();
        gardenId = in.readString();
        doseId = in.readString();
        quantity = in.readFloat();
    }
}
