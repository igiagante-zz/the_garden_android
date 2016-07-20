package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributePerPlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.FlavorRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Ignacio Giagante, on 26/4/16.
 */
public class PlantRealmToPlant implements Mapper<PlantRealm, Plant> {

    private final ImageRealmToImage toImage;
    private final FlavorRealmToFlavor toFlavor;
    private final AttributeRealmToAttribute toAttribute;
    private final PlagueRealmToPlague toPlague;

    // TODO - Refactor. This should not be here, because this object it's a Mapper.
    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public PlantRealmToPlant(@NonNull Context context){
        this.toImage = new ImageRealmToImage();
        this.toFlavor = new FlavorRealmToFlavor();
        this.toAttribute = new AttributeRealmToAttribute();
        this.toPlague = new PlagueRealmToPlague();

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name("garden.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);
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
            for (AttributePerPlantRealm attributePerPlantRealm : plantRealm.getAttributes()) {

                // get attribute using attributeId from AttributePerPlantRealm
                AttributeRealm attributeRealm = realm.where(AttributeRealm.class)
                        .equalTo(Table.ID,
                                attributePerPlantRealm.getAttributeId()).findFirst();
                // TODO - It should not execute a transaction inside a Mapper
                realm.executeTransaction(realmParam ->
                        // set the percentage to the attribute from AttributePerPlantRealm
                        attributeRealm.setPercentage(attributePerPlantRealm.getPercentage()));
                realm.close();

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

}
