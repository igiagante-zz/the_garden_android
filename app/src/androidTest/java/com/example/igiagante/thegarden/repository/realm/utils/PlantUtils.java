package com.example.igiagante.thegarden.repository.realm.utils;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.domain.entity.Plant;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class PlantUtils {

    /**
     * Create a list of plants without images
     *
     * @return plants
     */
    public static ArrayList<Plant> createPlants() {

        ArrayList<Plant> plants = new ArrayList<>();

        Plant plantOne = createPlant("1", "mango");
        Plant plantTwo = createPlant("2", "pera");
        Plant plantThree = createPlant("3", "naranja");

        plants.add(plantOne);
        plants.add(plantTwo);
        plants.add(plantThree);

        return plants;
    }

    /**
     * Create a list of two plants with two images each one
     * @return plants
     */
    public static ArrayList<Plant> createPlantsWithImages() {

        ArrayList<Plant> plants = new ArrayList<>();

        // plant one
        Plant plantOne = createPlantWithImages("1", "mango");

        Image imageOne = ImageUtils.createImage("1", "mango", true);
        Image imageTwo = ImageUtils.createImage("2", "mango2", false);

        ArrayList<Image> images = new ArrayList<>();
        images.add(imageOne);
        images.add(imageTwo);

        plantOne.setImages(images);

        // plant two
        Plant plantTwo = createPlantWithImages("2", "pera");

        Image imageThree = ImageUtils.createImage("3", "pera", true);
        Image imageFour = ImageUtils.createImage("4", "pera2", false);

        ArrayList<Image> imagesPlantTwo = new ArrayList<>();
        imagesPlantTwo.add(imageThree);
        imagesPlantTwo.add(imageFour);

        plantTwo.setImages(imagesPlantTwo);

        plants.add(plantOne);
        plants.add(plantTwo);

        return plants;
    }

    /**
     * Create one plant
     *
     * @param id   Id
     * @param name Plant's name
     * @return plant
     */
    public static Plant createPlant(String id, String name) {

        Plant plant = new Plant();
        plant.setId(id);
        plant.setName(name);
        plant.setSize(30);
        plant.setGardenId("1");
        plant.setFloweringTime("7 weeks");
        plant.setSeedDate(new Date());
        plant.setPhSoil(6);
        plant.setEcSoil(1);
        plant.setHarvest(60);
        plant.setDescription("Description");

        return plant;
    }

    /**
     * Create one plant with images
     *
     * @param id   Id
     * @param name Plant's name
     * @return plant
     */
    public static Plant createPlantWithImages(String id, String name) {

        Plant plant = createPlant(id, name);

        ArrayList<Image> images = new ArrayList<>();

        Image imageOne = ImageUtils.createImage("1", "mango", true);
        Image imageTwo = ImageUtils.createImage("2", "naranja", false);

        images.add(imageOne);
        images.add(imageTwo);

        plant.setImages(images);

        return plant;
    }

    public static Plant createPlantWithAll(String id, String name) {

        Plant plant = createPlantWithImages(id, name);

        // ADD FLAVORS
        plant.setFlavors(createFlavors());

        // ADD ATTRIBUTES
        plant.setAttributes(createAttributes());

        // ADD PLAGUES
        plant.setPlagues(createPlagues());

        return plant;
    }

    public static Plant createPlantWithAllForUpdate(String id, String name) {

        Plant plant = createPlantWithImages(id, name);

        // UPDATE FLAVORS
        ArrayList<Flavor> flavors = new ArrayList<>();

        Flavor flavorOne = createFlavor("1", "lemon", "/images/flavors/lemon");
        Flavor flavorThree = createFlavor("3", "soil", "/images/flavors/soil");

        flavors.add(flavorOne);
        flavors.add(flavorThree);

        plant.setFlavors(flavors);

        // UPDATE ATTRIBUTES
        ArrayList<Attribute> attributes = new ArrayList<>();

        Attribute attributeOne = createAttribute("1", "Euphoric", "effects", 50);
        Attribute attributeThree = createAttribute("3", "Symptoms", "headache", 60);

        attributes.add(attributeOne);
        attributes.add(attributeThree);

        plant.setAttributes(attributes);

        // UPDATE PLAGUES
        ArrayList<Plague> plagues = new ArrayList<>();

        Plague plagueOne = createPlague("1", "caterpillar", "/images/flavors/caterpillar");
        Plague PlagueTwo = createPlague("2", "trip", "/images/flavors/trip");
        Plague PlagueThree = createPlague("4", "fly_white", "/images/flavors/fly_white");

        plagues.add(plagueOne);
        plagues.add(PlagueTwo);
        plagues.add(PlagueThree);

        plant.setPlagues(plagues);

        return plant;
    }

    /**
     * Create a list of attributes
     *
     */
    public static ArrayList<Attribute> createAttributes() {

        ArrayList<Attribute> attributes = new ArrayList<>();

        Attribute attributeOne = createAttribute("1", "Euphoric", "effects", 30);
        Attribute attributeTwo = createAttribute("2", "Insomnia", "medicinal", 50);
        Attribute attributeThree = createAttribute("3", "Symptoms", "headache", 70);

        attributes.add(attributeOne);
        attributes.add(attributeTwo);
        attributes.add(attributeThree);

        return attributes;
    }

    /**
     * Create a list of plagues
     *
     * @return plagues
     */
    public static ArrayList<Plague> createPlagues() {

        ArrayList<Plague> plagues = new ArrayList<>();

        Plague plagueOne = createPlague("1", "caterpillar", "/image/plagues/caterpillar.jpg");
        Plague plagueTwo = createPlague("2", "trip", "/image/plagues/trip.jpg");
        Plague plagueThree = createPlague("3", "spider", "/image/plagues/spider.jpg");
        Plague plagueFour = createPlague("4", "fly_white", "/image/plagues/fly_white.jpg");

        plagues.add(plagueOne);
        plagues.add(plagueTwo);
        plagues.add(plagueThree);
        plagues.add(plagueFour);

        return plagues;
    }

    /**
     * Create a list of flavors
     *
     * @return flavors
     */
    public static ArrayList<Flavor> createFlavors() {

        ArrayList<Flavor> flavors = new ArrayList<>();

        Flavor flavorOne = createFlavor("1", "lemon", "/images/flavors/lemon");
        Flavor flavorTwo = createFlavor("2", "wood", "/images/flavors/wood");
        Flavor flavorThree = createFlavor("3", "soil", "/images/flavors/soil");

        flavors.add(flavorOne);
        flavors.add(flavorTwo);
        flavors.add(flavorThree);

        return flavors;
    }

    public static Flavor createFlavor(String id, String name, String imageUrl) {
        Flavor flavor = new Flavor();
        flavor.setId(id);
        flavor.setName(name);
        flavor.setImageUrl(imageUrl);
        return flavor;
    }

    public static Attribute createAttribute(String id, String name, String type, int percentage) {
        Attribute attribute = new Attribute();
        attribute.setId(id);
        attribute.setName(name);
        attribute.setType(type);
        attribute.setPercentage(percentage);
        return attribute;
    }

    public static Plague createPlague(String id, String name, String imageUrl) {
        Plague plague = new Plague();
        plague.setId(id);
        plague.setName(name);
        plague.setImageUrl(imageUrl);
        return plague;
    }
}
