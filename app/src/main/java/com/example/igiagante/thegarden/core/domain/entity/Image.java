package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.File;

/**
 * @author Ignacio Giagante, on 15/4/16.
 */
public class Image implements Parcelable {

    public Image() {}

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    @SerializedName("type")
    private String type;

    @SerializedName("size")
    private int size;

    @SerializedName("main")
    private boolean main;

    private File file;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(thumbnailUrl);
        dest.writeString(type);
        dest.writeInt(size);
        dest.writeInt(main ? 1 : 0);
        dest.writeSerializable(file);
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {

        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    private Image(Parcel in) {
        id = in.readString();
        name = in.readString();
        url = in.readString();
        thumbnailUrl = in.readString();
        type = in.readString();
        size = in.readInt();
        main = in.readInt() == 1;
        file = (File) in.readSerializable();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n");
        stringBuilder.append("id = " + this.getId() + "\n");
        stringBuilder.append("name = " + this.getName() + "\n");
        stringBuilder.append("url = " + this.getUrl() + "\n");
        stringBuilder.append("thumbnailUrl = " + this.getThumbnailUrl() + "\n");
        stringBuilder.append("type = " + this.getType() + "\n");
        stringBuilder.append("size = " + this.getSize() + "\n");
        stringBuilder.append(" main = " + this.isMain() + "\n");

        return stringBuilder.toString();
    }
}
