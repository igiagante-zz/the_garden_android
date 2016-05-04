package com.example.igiagante.thegarden.home.plants.repository.realm.mapper;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.home.plants.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.home.plants.repository.realm.modelRealm.PlantRealm;

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

        // create plant realm object and set id
        PlantRealm plantRealm = realm.createObject(PlantRealm.class);
        plantRealm.setId(plant.getId());

        // copy values which should be updated
        copy(plant, plantRealm);

        RealmList<ImageRealm> imagesRealm = new RealmList<>();

        if(plant.getImages() != null) {
            for ( Image image : plant.getImages() ){
                // create image realm object and set id
                ImageRealm imageRealm = realm.createObject(ImageRealm.class);
                imageRealm.setId(image.getId());
                // copy values which should be updated
                imagesRealm.add(toImageRealm.copy(image, imageRealm));
            }
        }

        plantRealm.setImages(imagesRealm);

        return plantRealm;
    }

    @Override
    public PlantRealm copy(Plant plant, PlantRealm plantRealm) {

        plantRealm.setName(plant.getName());
        plantRealm.setGardenId(plant.getGardenId());
        plantRealm.setSize(plant.getSize());
        plantRealm.setPhSoil(plant.getPhSoil());
        plantRealm.setEcSoil(plant.getEcSoil());
        plantRealm.setHarvest(plant.getHarvest());

        return plantRealm;
    }
}
