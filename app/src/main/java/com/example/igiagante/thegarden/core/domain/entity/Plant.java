package com.example.igiagante.thegarden.core.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Ignacio Giagante, on 15/4/16.
 */
public class Plant implements Parcelable {

    public Plant() {
    }

    public Plant(PlantBuilder builder) {
        this.id = builder.plant.getId();
        this.gardenId = builder.plant.getGardenId();
        this.seedDate = builder.plant.getSeedDate();
        this.name = builder.plant.getName();
        this.phSoil = builder.plant.getPhSoil();
        this.ecSoil = builder.plant.getEcSoil();
        this.floweringTime = builder.plant.getFloweringTime();
        this.size = builder.plant.getSize();
        this.harvest = builder.plant.getHarvest();
        this.description = builder.plant.getDescription();
        this.images = builder.mImages;
        this.flavors = builder.flavors;
        this.attributes = builder.attributes;
        this.plagues = builder.plagues;
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

    @SerializedName("description")
    private String description;

    @SerializedName("mImages")
    private List<Image> images = new ArrayList<>();

    @SerializedName("flavors")
    private List<Flavor> flavors = new ArrayList<>();

    @SerializedName("attributes")
    private List<Attribute> attributes = new ArrayList<>();

    @SerializedName("plagues")
    private List<Plague> plagues = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Plague> getPlagues() {
        return plagues;
    }

    public void setPlagues(List<Plague> plagues) {
        this.plagues = plagues;
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
        dest.writeString(description);
        dest.writeList(images);
        dest.writeList(flavors);
        dest.writeList(attributes);
        dest.writeList(plagues);
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
        description = in.readString();
        in.readList(images, this.getClass().getClassLoader());
        in.readList(flavors, this.getClass().getClassLoader());
        in.readList(attributes, this.getClass().getClassLoader());
        in.readList(plagues, this.getClass().getClassLoader());
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
            stringBuilder.append("************** Number of Images: " + this.getImages().size() + "  *****************");
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

    /**
     * This class represent the object which will be contain all the data entered in each wizards' step
     */
    public static class PlantBuilder {

        /**
         * Used to keep all the data related to the plant
         */
        private Plant plant;

        /**
         * Represent the mImages which belong to the plant
         */
        private ArrayList<Image> mImages;

        /**
         * Represent the flavors with which the plant could be identify
         */
        private ArrayList<Flavor> flavors;

        /**
         * Represent the attributes with which the plant could be identify
         */
        private ArrayList<Attribute> attributes;

        /**
         * Represent the plagues with which the plant could be identify
         */
        private ArrayList<Plague> plagues;

        public PlantBuilder() {
            plant = new Plant();
            mImages = new ArrayList<>();
            flavors = new ArrayList<>();
            attributes = new ArrayList<>();
            plagues = new ArrayList<>();
        }

        /**
         * Add the name of the plant to builder
         * @param plantName name
         * @return builder
         */
        public PlantBuilder addPlantName(String plantName) {
            plant.setName(plantName);
            return this;
        }

        /**
         * Add the Ph Soil to builder
         * @param phSoil ph soil
         * @return builder
         */
        public PlantBuilder addPhSoil(float phSoil) {
            plant.setPhSoil(phSoil);
            return this;
        }

        /**
         * Add the Ec Soil to builder
         * @param ecSoil ec soil
         * @return builder
         */
        public PlantBuilder addEcSoil(float ecSoil) {
            plant.setEcSoil(ecSoil);
            return this;
        }

        /**
         * Add the flowering time of the plant to builder
         * @param floweringTime name
         * @return builder
         */
        public PlantBuilder addFloweringTime(String floweringTime) {
            plant.setFloweringTime(floweringTime);
            return this;
        }

        /**
         * Add the harvest to builder
         * @param harvest indicate how much was the harvest
         * @return builder
         */
        public PlantBuilder addHarvest(int harvest) {
            plant.setHarvest(harvest);
            return this;
        }

        /**
         * Add the genoytpe to builder
         * @param genotype indicate the genotype of the plant
         * @return builder
         */
        public PlantBuilder addGenotype(String genotype) {
            plant.setGenotype(genotype);
            return this;
        }

        /**
         * Add the size to builder
         * @param size indicate how long is the plant in one moment
         * @return builder
         */
        public PlantBuilder addSize(int size) {
            plant.setSize(size);
            return this;
        }

        /**
         * Add the plants description to builder
         * @param description plant's description
         * @return builder
         */
        public PlantBuilder addDescription(String description) {
            plant.setDescription(description);
            return this;
        }

        public PlantBuilder addImages(Collection<Image> images, boolean carousel) {

            Log.i("IMAGES", "TESTING size: " + this.mImages.size());

            ArrayList<Image> imagesList = (ArrayList<Image>) images;

            if(carousel || images.isEmpty()) {
                this.mImages = imagesList;
            } else {
                this.mImages.addAll(imagesList);
            }

            Log.i("IMAGES", "TESTING size: " + this.mImages.size());

            return this;
        }

        public PlantBuilder addFlavors(Collection<Flavor> flavors) {
            this.flavors = (ArrayList<Flavor>) flavors;
            return this;
        }

        public PlantBuilder addAttributes(Collection<Attribute> attributes) {
            this.attributes = (ArrayList<Attribute>) attributes;
            return this;
        }

        public PlantBuilder addPlagues(Collection<Plague> plagues) {
            this.plagues = (ArrayList<Plague>) plagues;
            return this;
        }

        public Plant build() {
            return new Plant(this);
        }

        private void addImages(ArrayList<Image> imagesList) {
            for(Image image : imagesList) {
                if(!mImages.contains(image)) {
                    mImages.add(image);
                }
            }
/*
            for (int i = 0; i < imagesList.size(); i++) {
                for (int j = 0; j < mImages.size(); j++) {
                    if(imagesList.get(i).getName().equals(mImages.get(j))){

                    }
                }
            }*/
        }
    }
}
