package com.example.igiagante.thegarden.plants.repository.realm.modelRealm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by igiagante on 26/4/16.
 */
public class PlantRealm extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String name;

    @Required
    private String gardenId;

    private int size;

    private float phSoil;

    private float ecSoil;

    private int harvest;

    private RealmList<ImageRealm> images;

    private RealmList<FlavorRealm> flavors;

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

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
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

    public int getHarvest() {
        return harvest;
    }

    public void setHarvest(int harvest) {
        this.harvest = harvest;
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
}
