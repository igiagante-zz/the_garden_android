package com.example.igiagante.thegarden.core.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.File;

/**
 * Created by igiagante on 19/4/16.
 */
public class Image {

    @SerializedName("_id")
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

    @Override public String toString() {
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
