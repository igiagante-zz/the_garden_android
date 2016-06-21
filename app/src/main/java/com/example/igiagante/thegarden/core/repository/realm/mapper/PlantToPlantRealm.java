package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.support.annotation.NonNull;
import android.util.Log;

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

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 27/4/16.
 */
public class PlantToPlantRealm implements Mapper<Plant, PlantRealm> {

    private final Realm realm;
    private final ImageToImageRealm toImageRealm;
    private final FlavorToFlavorRealm toFlavorRealm;
    private final AttributeToAttributeRealm toAttributeRealm;
    private final PlagueToPlagueRealm toPlagueRealm;

    public PlantToPlantRealm(Realm realm){
        this.realm = realm;
        this.toImageRealm = new ImageToImageRealm(realm);
        this.toFlavorRealm = new FlavorToFlavorRealm(realm);
        this.toAttributeRealm = new AttributeToAttributeRealm(realm);
        this.toPlagueRealm = new PlagueToPlagueRealm(realm);
    }

    @Override
    public PlantRealm map(@NonNull Plant plant) {
        // create plant realm object and set id
        PlantRealm plantRealm = realm.createObject(PlantRealm.class);
        plantRealm.setId(plant.getId());

        // copy values which should be updated
        copy(plant, plantRealm);

        // images realm list
        RealmList<ImageRealm> imagesRealm = new RealmList<>();


        // flavors realm list
        RealmList<FlavorRealm> flavorsRealm = new RealmList<>();

        // attributes realm list
        RealmList<AttributeRealm> attributesRealm = new RealmList<>();

        // plagues realm list
        RealmList<PlagueRealm> plaguesRealm = new RealmList<>();

        // add images
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

        // add flavors
        if(plant.getFlavors() != null) {
            for ( Flavor flavor : plant.getFlavors()) {
                // create flavor realm object and set id
                FlavorRealm flavorRealm = realm.createObject(FlavorRealm.class);
                // copy values which should be updated
                flavorsRealm.add(toFlavorRealm.copy(flavor, flavorRealm));
            }
        }

        plantRealm.setFlavors(flavorsRealm);

        // add attributes
        if(plant.getAttributes() != null) {
            for ( Attribute attribute : plant.getAttributes()) {
                AttributeRealm attributeRealm = realm.where(AttributeRealm.class).equalTo(PlantTable.Attribute.ID, attribute.getId()).findFirst();
                attributesRealm.add(attributeRealm);
            }
        }

        plantRealm.setAttributes(attributesRealm);

        // add plagues
        if(plant.getPlagues() != null) {
            for ( Plague plague : plant.getPlagues()) {
                PlagueRealm plagueRealm = realm.where(PlagueRealm.class).equalTo(PlantTable.Attribute.ID, plague.getId()).findFirst();
                plaguesRealm.add(plagueRealm);
            }
        }

        plantRealm.setPlagues(plaguesRealm);

        return plantRealm;
    }

    @Override
    public PlantRealm copy(@NonNull Plant plant, @NonNull PlantRealm plantRealm) {

        plantRealm.setName(plant.getName());
        plantRealm.setGardenId(plant.getGardenId());
        plantRealm.setSeedDate(plant.getSeedDate());
        plantRealm.setFloweringTime(plant.getFloweringTime());
        plantRealm.setGenotype(plant.getGenotype());
        plantRealm.setSize(plant.getSize());
        plantRealm.setPhSoil(plant.getPhSoil());
        plantRealm.setEcSoil(plant.getEcSoil());
        plantRealm.setHarvest(plant.getHarvest());
        plantRealm.setDescription(plant.getDescription());

        return plantRealm;
    }
}
