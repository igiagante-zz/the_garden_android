package com.example.igiagante.thegarden.repository.realm.utils;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class GardenUtils {

    private final static String ID = "1";
    private final static String NAME = "la selva";
    private final static Date DATE = new Date();

    public static Garden createGarden(String id, String name, Date startDate, Date endDate) {

        Garden garden = new Garden();
        garden.setId(id);
        garden.setName(name);
        garden.setStartDate(startDate);
        garden.setEndDate(endDate);

        return garden;
    }

    public static ArrayList<Garden> createGardens() {

        ArrayList<Garden> gardens = new ArrayList<>();

        Garden gardenOne = createGarden("1", "la selva", new Date(), DATE);
        Garden gardenTwo = createGarden("2", "yunga", new Date(), DATE);
        Garden gardenThree = createGarden("3", "la casita", new Date(), DATE);

        gardens.add(gardenOne);
        gardens.add(gardenTwo);
        gardens.add(gardenThree);

        return gardens;
    }

    public static Garden createGardenWithPlants() {
        ArrayList<Plant> plants = PlantUtils.createPlantsWithImages();
        Garden garden = createGarden(ID, NAME, new Date(), DATE);
        garden.setPlants(plants);
        return garden;
    }
}
