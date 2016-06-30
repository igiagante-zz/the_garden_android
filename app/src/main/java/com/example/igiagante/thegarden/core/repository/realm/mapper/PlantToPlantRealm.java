package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributePerPlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.FlavorRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantTable;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

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
        copy(plant, plantRealm, false);

        return plantRealm;
    }

    @Override
    public PlantRealm copy(Plant plant, PlantRealm plantRealm) {
        return null;
    }

    @Override
    public PlantRealm copy(@NonNull Plant plant, @NonNull PlantRealm plantRealm, boolean update) {

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

        // images realm list
        RealmList<ImageRealm> imagesRealm = new RealmList<>();

        // flavors realm list
        RealmList<FlavorRealm> flavorsRealm = new RealmList<>();

        // attributes realm list
        RealmList<AttributePerPlantRealm> attributesRealm = new RealmList<>();

        // plagues realm list
        RealmList<PlagueRealm> plaguesRealm = new RealmList<>();

        // add images
        if(plant.getImages() != null) {
            for ( Image image : plant.getImages() ){
                if(update) {
                    List<ImageRealm> imagesToBeDeleted = getImagesToBeDeleted(plant.getResourcesIds());
                    for (ImageRealm imageRealm : imagesToBeDeleted) {
                        // TODO - It should not execute a transaction inside a Mapper
                        realm.executeTransaction(realmParam -> imageRealm.deleteFromRealm());
                        realm.close();
                    }
                } else {
                    // create image realm object and set id
                    ImageRealm imageRealm = realm.createObject(ImageRealm.class);
                    imageRealm.setId(image.getId());
                    imagesRealm.add(toImageRealm.copy(image, imageRealm));
                }
            }
        }

        plantRealm.setImages(imagesRealm);

        // add flavors
        if(plant.getFlavors() != null) {
            for ( Flavor flavor : plant.getFlavors()) {
                FlavorRealm flavorRealm = realm.where(FlavorRealm.class).equalTo(PlantTable.Flavor.ID, flavor.getId()).findFirst();
                if(flavorRealm == null) {
                    // create flavor realm object and set id
                    flavorRealm = realm.createObject(FlavorRealm.class);
                    flavorRealm.setId(flavor.getId());
                }
                // copy values which should be updated
                flavorsRealm.add(toFlavorRealm.copy(flavor, flavorRealm));
            }
        }

        plantRealm.setFlavors(flavorsRealm);

        // add attributes
        if(plant.getAttributes() != null) {
            for ( Attribute attribute : plant.getAttributes()) {
                AttributePerPlantRealm attributePerPlantRealm = realm.where(AttributePerPlantRealm.class)
                        .equalTo(PlantTable.AttributePerPlant.ATTRIBUTE_ID, attribute.getId()).findFirst();
                if(attributePerPlantRealm == null) {
                    // create attributePerPlant realm object and set id
                    attributePerPlantRealm = realm.createObject(AttributePerPlantRealm.class);
                    attributePerPlantRealm.setAttributeId(attribute.getId());
                }

                attributePerPlantRealm.setPercentage(attribute.getPercentage());

                attributesRealm.add(attributePerPlantRealm);
            }
        }

        plantRealm.setAttributes(attributesRealm);

        // add plagues
        if(plant.getPlagues() != null) {
            for ( Plague plague : plant.getPlagues()) {
                PlagueRealm plagueRealm = realm.where(PlagueRealm.class).equalTo(PlantTable.Plague.ID, plague.getId()).findFirst();
                plaguesRealm.add(plagueRealm);
            }
        }

        plantRealm.setPlagues(plaguesRealm);

        return plantRealm;
    }

    private List<ImageRealm> getImagesToBeDeleted(List<String> resourcesIds) {

        List<ImageRealm> list = new ArrayList<>();

        RealmResults<ImageRealm> all = realm.where(ImageRealm.class).findAll();

        for (int i = 0; i < all.size(); i++) {
            for (int j = 0; j < resourcesIds.size(); j++) {
                if(all.get(i).getId().equals(resourcesIds.get(j))) {
                    list.add(all.get(i));
                }
            }
        }

        return list;
    }
}
