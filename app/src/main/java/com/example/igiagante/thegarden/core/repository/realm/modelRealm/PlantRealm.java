package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Ignacio Giagante, on 26/4/16.
 */
public class PlantRealm extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String gardenId;

    @Required
    private String name;

    private Date seedDate;

    private float phSoil;

    private float ecSoil;

    private String floweringTime;

    private String genotype;

    private int size;

    private int harvest;

    private String description;

    private RealmList<ImageRealm> images;

    private RealmList<FlavorRealm> flavors;

    private RealmList<AttributePerPlantRealm> attributes;

    private RealmList<PlagueRealm> plagues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSeedDate() {
        return seedDate;
    }

    public void setSeedDate(Date seedDate) {
        this.seedDate = seedDate;
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

    public String getFloweringTime() {
        return floweringTime;
    }

    public void setFloweringTime(String floweringTime) {
        this.floweringTime = floweringTime;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public RealmList<ImageRealm> getImages() {
        return images;
    }

    public void setImages(RealmList<ImageRealm> images) {
        this.images = images;
    }

    public RealmList<FlavorRealm> getFlavors() {
        return flavors;
    }

    public void setFlavors(RealmList<FlavorRealm> flavors) {
        this.flavors = flavors;
    }

    public RealmList<AttributePerPlantRealm> getAttributes() {
        return attributes;
    }

    public void setAttributes(RealmList<AttributePerPlantRealm> attributes) {
        this.attributes = attributes;
    }

    public RealmList<PlagueRealm> getPlagues() {
        return plagues;
    }

    public void setPlagues(RealmList<PlagueRealm> plagues) {
        this.plagues = plagues;
    }
}
