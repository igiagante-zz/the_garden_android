package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealmToGarden implements Mapper<GardenRealm, Garden> {

    private final PlantRealmToPlant toPlant;

    public GardenRealmToGarden(@NonNull Context context) {
        this.toPlant = new PlantRealmToPlant(context);
    }

    @Override
    public Garden map(GardenRealm gardenRealm) {
        Garden garden = new Garden();
        garden.setId(gardenRealm.getId());
        copy(gardenRealm, garden);
        return garden;
    }

    @Override
    public Garden copy(GardenRealm gardenRealm, Garden garden) {
        garden.setName(gardenRealm.getName());
        garden.setStartDate(gardenRealm.getStartDate());
        garden.setEndDate(gardenRealm.getEndDate());

        ArrayList<Plant> plants = new ArrayList<>();

        // add plants
        if(gardenRealm.getPlants() != null) {
            for (PlantRealm plantRealm : gardenRealm.getPlants()) {
                plants.add(toPlant.map(plantRealm));
            }
        }

        garden.setPlants(plants);

        return garden;
    }

}
