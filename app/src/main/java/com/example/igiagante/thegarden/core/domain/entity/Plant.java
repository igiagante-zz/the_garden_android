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
public class Plant implements Parcelable {

    public Plant() {
    }

    @SerializedName("_id")
    private String id;

    @SerializedName("gardenId")
    private String gardenId;

    @SerializedName("seedDate")
    private Date seedDate;

    @SerializedName("name")
    private String name;

    @SerializedName("phSoil")
    private float phSoil;

    @SerializedName("ecSoil")
    private float ecSoil;

    @SerializedName("floweringTime")
    private String floweringTime;

    @SerializedName("genotype")
    private String genotype;

    @SerializedName("size")
    private int size;

    @SerializedName("harvest")
    private int harvest;

    @SerializedName("images")
    private List<Image> images = new ArrayList<>();

    @SerializedName("flavors")
    private List<Flavor> flavors = new ArrayList<>();

    @SerializedName("attributes")
    private List<Attribute> attributes = new ArrayList<>();

    @SerializedName("resourcesIds")
    private List<String> resourcesIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSeedDate() {
        return seedDate;
    }

    public void setSeedDate(Date seedDate) {
        this.seedDate = seedDate;
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

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getFloweringTime() {
        return floweringTime;
    }

    public void setFloweringTime(String floweringTime) {
        this.floweringTime = floweringTime;
    }

    public int getHarvest() {
        return harvest;
    }

    public void setHarvest(int harvest) {
        this.harvest = harvest;
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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
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
        dest.writeString(gardenId);
        dest.writeString(name);
        dest.writeFloat(phSoil);
        dest.writeFloat(ecSoil);
        dest.writeString(floweringTime);
        dest.writeString(genotype);
        dest.writeInt(size);
        dest.writeInt(harvest);
        dest.writeList(images);
        dest.writeList(flavors);
        dest.writeList(attributes);
        dest.writeList(resourcesIds);
    }

    public static final Parcelable.Creator<Plant> CREATOR = new Parcelable.Creator<Plant>() {
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };

    private Plant(Parcel in) {
        id = in.readString();
        gardenId = in.readString();
        name = in.readString();
        phSoil = in.readFloat();
        ecSoil = in.readFloat();
        floweringTime = in.readString();
        genotype = in.readString();
        size = in.readInt();
        harvest = in.readInt();
        in.readList(images, this.getClass().getClassLoader());
        in.readList(flavors, this.getClass().getClassLoader());
        in.readList(attributes, this.getClass().getClassLoader());
        in.readList(resourcesIds, this.getClass().getClassLoader());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Plant Details *****\n");
        stringBuilder.append("id = " + this.getId() + "\n");
        stringBuilder.append("name = " + this.getName() + "\n");
        stringBuilder.append("size = " + this.getSize() + "\n");
        stringBuilder.append("phSoil = " + this.getPhSoil() + "\n");
        stringBuilder.append("ecSoil = " + this.getEcSoil() + "\n");
        stringBuilder.append("gardenId = " + this.getGardenId() + "\n");
        stringBuilder.append("harvest = " + this.getHarvest() + "\n");

        if (this.getImages() != null) {
            stringBuilder.append("\n");
            stringBuilder.append("************** Number of images: " + this.getImages().size() + "  *****************");
            stringBuilder.append("\n");

            for (Image image : this.getImages()) {
                stringBuilder.append("\n");
                stringBuilder.append("******** Image  Details **********");
                stringBuilder.append("\n");
                stringBuilder.append(image.toString());
            }
        }

        return stringBuilder.toString();
    }
}
