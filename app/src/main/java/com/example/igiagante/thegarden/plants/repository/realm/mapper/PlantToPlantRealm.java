package com.example.igiagante.thegarden.plants.repository.realm.mapper;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.plants.domain.entity.Image;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.PlantRealm;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by igiagante on 27/4/16.
 */
public class PlantToPlantRealm implements Mapper<Plant, PlantRealm> {

    private final Realm realm;
    private final ImageToImageRealm toImageRealm;

    public PlantToPlantRealm(Realm realm){
        this.realm = realm;
        this.toImageRealm = new ImageToImageRealm(realm);
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

        RealmList<ImageRealm> imagesRealm = new RealmList<>();

        if(plant.getImages() != null) {
            for ( Image image : plant.getImages() ){
                imagesRealm.add(toImageRealm.map(image));
            }
        }

        plantRealm.setImages(imagesRealm);

        return plantRealm;
    }
}
