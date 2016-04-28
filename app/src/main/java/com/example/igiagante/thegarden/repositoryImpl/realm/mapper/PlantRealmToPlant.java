package com.example.igiagante.thegarden.repositoryImpl.realm.mapper;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.plants.domain.entity.Image;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.ImageRealm;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;

import java.util.ArrayList;

/**
 * Created by igiagante on 26/4/16.
 */
public class PlantRealmToPlant implements Mapper<PlantRealm, Plant> {

    private final ImageRealmToImage toImage;

    public PlantRealmToPlant(){
        this.toImage = new ImageRealmToImage();
    }

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

        ArrayList<Image> images = new ArrayList<>();

        if(plantRealm.getImages() != null) {
            for (ImageRealm imageRealm : plantRealm.getImages()) {
                images.add(toImage.map(imageRealm));
            }
        }

        plant.setImages(images);

        return plant;
    }

}
