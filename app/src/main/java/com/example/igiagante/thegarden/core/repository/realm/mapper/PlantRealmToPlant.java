package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 26/4/16.
 */
public class PlantRealmToPlant implements Mapper<PlantRealm, Plant> {

    private final ImageRealmToImage toImage;

    public PlantRealmToPlant(){
        this.toImage = new ImageRealmToImage();
    }

    @Override
    public Plant map(@NonNull PlantRealm plantRealm) {

        Plant plant = new Plant();

        plant.setId(plantRealm.getId());
        copy(plantRealm, plant);

        ArrayList<Image> images = new ArrayList<>();

        if(plantRealm.getImages() != null) {
            for (ImageRealm imageRealm : plantRealm.getImages()) {
                images.add(toImage.map(imageRealm));
            }
        }

        plant.setImages(images);

        return plant;
    }

    @Override
    public Plant copy(@NonNull PlantRealm plantRealm, @NonNull Plant plant) {

        plant.setName(plantRealm.getName());
        plant.setGardenId(plantRealm.getGardenId());
        plant.setSize(plantRealm.getSize());
        plant.setPhSoil(plantRealm.getPhSoil());
        plant.setEcSoil(plantRealm.getEcSoil());
        plant.setHarvest(plantRealm.getHarvest());

        return plant;
    }
}
