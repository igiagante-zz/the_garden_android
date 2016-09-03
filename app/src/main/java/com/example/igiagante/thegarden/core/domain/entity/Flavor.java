package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ignacio Giagante, on 19/4/16.
 */
public class Flavor implements Parcelable {

    public Flavor() {
    }

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imageUrl;

    private String localPath;

    @SerializedName("mongoId")
    private String mongoId;

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

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
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
        dest.writeString(localPath);
        dest.writeString(mongoId);
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
        localPath = in.readString();
        mongoId = in.readString();
    }
}
