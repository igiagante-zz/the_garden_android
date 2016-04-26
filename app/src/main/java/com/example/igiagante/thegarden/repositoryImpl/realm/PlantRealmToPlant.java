package com.example.igiagante.thegarden.repositoryImpl.realm;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;

/**
 * Created by igiagante on 26/4/16.
 */
public class PlantRealmToPlant implements Mapper<PlantRealm, Plant> {

    @Override
    public Plant map(PlantRealm plantRealm) {

        Plant plant = new Plant();

        plant.setId(plantRealm.getId());
        plant.setName(plantRealm.getName());
        plant.setGardenId(plantRealm.getGardenId());
        plant.setSize(plantRealm.getSize());
        plant.setPhSoil(plantRealm.getPhSoil());
        plant.setEcSoil(plantRealm.getEcSoil());
        plant.setHarvest(plantRealm.getHarvest());

        return plant;
    }
}
