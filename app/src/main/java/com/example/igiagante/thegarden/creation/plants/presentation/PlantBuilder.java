package com.example.igiagante.thegarden.creation.plants.presentation;

/**
 * @author Ignacio Giagante, on 11/5/16.
 */

import android.widget.Toast;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

/**
 * This class represent the object which will be contain all the data entered in each wizards' step
 */
@PerActivity
public class PlantBuilder {

    /**
     * Used to keep all the data related to the plant
     */
    private Plant plant;

    /**
     * Represent the images which belong to the plant
     */
    private ArrayList<Image> images;

    /**
     * Represent the flavors with which the plant could be identify
     */
    private ArrayList<Flavor> flavors;

    /**
     * Represent the attributes with which the plant could be identify
     */
    private ArrayList<Attribute> attributes;

    @Inject
    public PlantBuilder() {
        plant = new Plant();
        images = new ArrayList<>();
        flavors = new ArrayList<>();
        attributes = new ArrayList<>();
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
     * Add the harvest to builder
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

    public PlantBuilder addImages(Collection<Image> images) {
        this.images = (ArrayList<Image>) images;
        return this;
    }
}
