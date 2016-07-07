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
        this.id = builder.id;
        this.gardenId = builder.gardenId;
        this.seedDate = builder.seedDate;
        this.name = builder.name;
        this.phSoil = builder.phSoil;
        this.ecSoil = builder.ecSoil;
        this.floweringTime = builder.floweringTime;
        this.genotype = builder.genotype;
        this.size = builder.size;
        this.harvest = builder.harvest;
        this.description = builder.description;
        this.images = builder.mImages;
        this.flavors = builder.flavors;
        this.attributes = builder.attributes;
        this.plagues = builder.plagues;
        this.resourcesIds = builder.resourcesIds;
    }

    public Plant(Plant plant) {
        this.id = plant.id;
        this.gardenId = plant.gardenId;
        this.seedDate = plant.seedDate;
        this.name = plant.name;
        this.phSoil = plant.phSoil;
        this.ecSoil = plant.ecSoil;
        this.floweringTime = plant.floweringTime;
        this.genotype = plant.genotype;
        this.size = plant.size;
        this.harvest = plant.harvest;
        this.description = plant.description;
        this.images = plant.images;
        this.flavors = plant.flavors;
        this.attributes = plant.attributes;
        this.plagues = plant.plagues;
        this.resourcesIds = plant.resourcesIds;
    }

    @SerializedName("id")
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

    @SerializedName("images")
    private List<Image> images = new ArrayList<>();

    @SerializedName("flavors")
    private List<Flavor> flavors = new ArrayList<>();

    @SerializedName("attributes")
    private List<Attribute> attributes = new ArrayList<>();

    @SerializedName("plagues")
    private List<Plague> plagues = new ArrayList<>();

    @SerializedName("resourcesIds")
    private List<String> resourcesIds = new ArrayList<>();

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

    /**
     * This class represent the object which will be contain all the data entered in each wizards' step
     */
    public static class PlantBuilder {

        private String id;
        private String gardenId;
        private Date seedDate = new Date();
        private String name;
        private float phSoil;
        private float ecSoil;
        private String floweringTime;
        private String genotype;
        private int size;
        private int harvest;
        private String description;

        private boolean updatingPlant = false;

        /**
         * Represent the images which belong to the plant
         */
        private ArrayList<Image> mImages;

        /**
         * Represent the resources ids which identify each image
         */
        private ArrayList<String> resourcesIds;

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
            mImages = new ArrayList<>();
            flavors = new ArrayList<>();
            attributes = new ArrayList<>();
            plagues = new ArrayList<>();
        }

        /**
         * Add plant's id to builder
         * @param id Plant Id
         * @return builder
         */
        public PlantBuilder addPlantId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Add garden's id to builder
         * @param gardenId Garden Id
         * @return builder
         */
        public PlantBuilder addGardenId(String gardenId) {
            this.gardenId = gardenId;
            return this;
        }

        /**
         * Add plant's name to builder
         * @param plantName name
         * @return builder
         */
        public PlantBuilder addPlantName(String plantName) {
            this.name = plantName;
            return this;
        }

        /**
         * Add Ph Soil to builder
         * @param phSoil ph soil
         * @return builder
         */
        public PlantBuilder addPhSoil(float phSoil) {
            this.phSoil = phSoil;
            return this;
        }

        /**
         * Add Ec Soil to builder
         * @param ecSoil ec soil
         * @return builder
         */
        public PlantBuilder addEcSoil(float ecSoil) {
            this.ecSoil = ecSoil;
            return this;
        }

        /**
         * Add flowering time of the plant to builder
         * @param floweringTime name
         * @return builder
         */
        public PlantBuilder addFloweringTime(String floweringTime) {
            this.floweringTime = floweringTime;
            return this;
        }

        /**
         * Add harvest to builder
         * @param harvest indicate how much was the harvest
         * @return builder
         */
        public PlantBuilder addHarvest(int harvest) {
            this.harvest = harvest;
            return this;
        }

        /**
         * Add genoytpe to builder
         * @param genotype indicate the genotype of the plant
         * @return builder
         */
        public PlantBuilder addGenotype(String genotype) {
            this.genotype = genotype;
            return this;
        }

        /**
         * Add size to builder
         * @param size indicate how long is the plant in one moment
         * @return builder
         */
        public PlantBuilder addSize(int size) {
            this.size = size;
            return this;
        }

        /**
         * Add plants description to builder
         * @param description plant's description
         * @return builder
         */
        public PlantBuilder addDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Add images to the builder
         * @param images list of images
         * @param carousel indicate if the images come from the carousel
         * @return builder
         */
        public PlantBuilder addImages(ArrayList<Image> images, boolean carousel) {
            // TODO - Refactor
            if(carousel || images.isEmpty()) {
                this.mImages = images;
            } else {
                this.mImages.addAll(images);
            }
            if(this.updatingPlant){
                this.mImages = filterImagesWithoutFile(mImages);
            }
            return this;
        }

        public void setUpdatingPlant(boolean updatingPlant) {
            this.updatingPlant = updatingPlant;
        }

        private ArrayList<Image> filterImagesWithoutFile(List<Image> images) {

            ArrayList<Image> temp = new ArrayList<>();

            for(Image image : images) {
                if(image.getFile() != null) {
                    temp.add(image);
                }
            }
            return temp;
        }

        public PlantBuilder addResourcesIds(List<String> resourcesIds) {
            this.resourcesIds = (ArrayList<String>)resourcesIds;
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
    }
}
