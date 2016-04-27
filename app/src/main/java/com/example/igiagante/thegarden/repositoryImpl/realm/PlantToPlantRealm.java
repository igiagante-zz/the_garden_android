package com.example.igiagante.thegarden.repositoryImpl.realm;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;

import io.realm.Realm;

/**
 * Created by igiagante on 27/4/16.
 */
public class PlantToPlantRealm implements Mapper<Plant, PlantRealm> {

    private final Realm realm;

    public PlantToPlantRealm(Realm realm){
        this.realm = realm;
    }

    @Override
    public PlantRealm map(Plant plant) {

        PlantRealm plantRealm = realm.createObject(PlantRealm.class);

        plantRealm.setId(plant.getId());
        plantRealm.setName(plant.getName());
        plantRealm.setGardenId(plant.getGardenId());
        plantRealm.setSize(plant.getSize());
        plantRealm.setPhSoil(plant.getPhSoil());
        plantRealm.setEcSoil(plant.getEcSoil());
        plantRealm.setHarvest(plant.getHarvest());

        return plantRealm;
    }
}
