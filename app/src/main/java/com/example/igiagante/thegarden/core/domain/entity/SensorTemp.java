package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
public class SensorTemp implements Parcelable {

    public SensorTemp() {
    }

    @SerializedName("date")
    private Date date;

    @SerializedName("temp")
    private float temp;

    @SerializedName("humidity")
    private int humidity;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(date);
        dest.writeFloat(temp);
        dest.writeInt(humidity);
    }

    public static final Parcelable.Creator<SensorTemp> CREATOR = new Parcelable.Creator<SensorTemp>() {
        public SensorTemp createFromParcel(Parcel in) {
            return new SensorTemp(in);
        }

        public SensorTemp[] newArray(int size) {
            return new SensorTemp[size];
        }
    };

    private SensorTemp(Parcel in) {
        date = (Date) in.readSerializable();
        temp = in.readFloat();
        humidity = in.readInt();
    }
}
