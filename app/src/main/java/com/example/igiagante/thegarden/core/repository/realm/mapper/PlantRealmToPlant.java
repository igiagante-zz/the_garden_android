package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.FlavorRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantTable;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 26/4/16.
 */
public class PlantRealmToPlant implements Mapper<PlantRealm, Plant> {

    private final ImageRealmToImage toImage;
    private final FlavorRealmToFlavor toFlavor;
    private final AttributeRealmToAttribute toAttribute;
    private final PlagueRealmToPlague toPlague;

    public PlantRealmToPlant(){
        this.toImage = new ImageRealmToImage();
        this.toFlavor = new FlavorRealmToFlavor();
        this.toAttribute = new AttributeRealmToAttribute();
        this.toPlague = new PlagueRealmToPlague();
    }

    @Override
    public Plant map(@NonNull PlantRealm plantRealm) {

        Plant plant = new Plant();

        plant.setId(plantRealm.getId());
        copy(plantRealm, plant);

        ArrayList<Image> images = new ArrayList<>();
        ArrayList<Flavor> flavors = new ArrayList<>();
        ArrayList<Attribute> attributes = new ArrayList<>();
        ArrayList<Plague> plagues = new ArrayList<>();

        // add images
        if(plantRealm.getImages() != null) {
            for (ImageRealm imageRealm : plantRealm.getImages()) {
                images.add(toImage.map(imageRealm));
            }
        }

        plant.setImages(images);

        // add flavors
        if(plantRealm.getFlavors() != null) {
            for (FlavorRealm flavorRealm : plantRealm.getFlavors()) {
                flavors.add(toFlavor.map(flavorRealm));
            }
        }

        plant.setFlavors(flavors);

        // add attributes
        if(plantRealm.getAttributes() != null) {
            for (AttributeRealm attributeRealm : plantRealm.getAttributes()) {
                attributes.add(toAttribute.map(attributeRealm));
            }
        }

        plant.setAttributes(attributes);

        // add plagues
        if(plantRealm.getPlagues() != null) {
            for (PlagueRealm plagueRealm : plantRealm.getPlagues()) {
                plagues.add(toPlague.map(plagueRealm));
            }
        }

        plant.setPlagues(plagues);

        return plant;
    }

    @Override
    public Plant copy(@NonNull PlantRealm plantRealm, @NonNull Plant plant) {

        plant.setName(plantRealm.getName());
        plant.setGardenId(plantRealm.getGardenId());
        plant.setSeedDate(plantRealm.getSeedDate());
        plant.setSize(plantRealm.getSize());
        plant.setPhSoil(plantRealm.getPhSoil());
        plant.setEcSoil(plantRealm.getEcSoil());
        plant.setFloweringTime(plantRealm.getFloweringTime());
        plant.setGenotype(plantRealm.getGenotype());
        plant.setHarvest(plantRealm.getHarvest());
        plant.setDescription(plantRealm.getDescription());

        return plant;
    }

    @Override
    public Plant copy(PlantRealm plantRealm, Plant plant, boolean update) {
        return null;
    }
}
