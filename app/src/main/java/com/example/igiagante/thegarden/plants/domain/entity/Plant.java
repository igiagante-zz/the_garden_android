package com.example.igiagante.thegarden.plants.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by igiagante on 15/4/16.
 */
public class Plant {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("size")
    private int size;

    @SerializedName("phSoil")
    private float phSoil;

    @SerializedName("ecSoil")
    private float ecSoil;

    @SerializedName("gardenId")
    private String gardenId;

    @SerializedName("images")
    private List<Image> images;

    @SerializedName("flavors")
    private List<Flavor> flavors;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getPhSoil() {
        return phSoil;
    }

    public void setPhSoil(float phSoil) {
        this.phSoil = phSoil;
    }

    public float getEcSoil() {
        return ecSoil;
    }

    public void setEcSoil(float ecSoil) {
        this.ecSoil = ecSoil;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
    }

    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Plant Details *****\n");
        stringBuilder.append("id = " + this.getId() + "\n");
        stringBuilder.append("name = " + this.getName() + "\n");
        stringBuilder.append("size = " + this.getSize() + "\n");
        stringBuilder.append("phSoil = " + this.getPhSoil() + "\n");
        stringBuilder.append("ecSoil = " + this.getEcSoil() + "\n");
        stringBuilder.append("gardenId = " + this.getGardenId() + "\n");
        stringBuilder.append("\n");
        stringBuilder.append("************** Images  *****************");

        for( Image image: this.getImages() ) {
            stringBuilder.append("\n");
            stringBuilder.append("******** Image  Details **********");
            stringBuilder.append(image.toString());
        }

        stringBuilder.append("\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
