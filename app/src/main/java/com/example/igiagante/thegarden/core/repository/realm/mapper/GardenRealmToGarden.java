package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealmToGarden implements Mapper<GardenRealm, Garden> {

    private final PlantRealmToPlant toPlant;
    private final IrrigationRealmToIrrigation toIrrigation;
    private final DoseRealmToDose toDose;

    public GardenRealmToGarden(@NonNull Context context) {
        this.toPlant = new PlantRealmToPlant(context);
        this.toIrrigation = new IrrigationRealmToIrrigation();
        this.toDose = new DoseRealmToDose();
    }

    @Override
    public Garden map(GardenRealm gardenRealm) {
        Garden garden = new Garden();
        garden.setId(gardenRealm.getId());
        copy(gardenRealm, garden);
        return garden;
    }

    @Override
    public Garden copy(GardenRealm gardenRealm, Garden garden) {
        garden.setName(gardenRealm.getName());
        garden.setStartDate(gardenRealm.getStartDate());
        garden.setEndDate(gardenRealm.getEndDate());

        ArrayList<Plant> plants = new ArrayList<>();

        // add plants
        if(gardenRealm.getPlants() != null) {
            for (PlantRealm plantRealm : gardenRealm.getPlants()) {
                plants.add(toPlant.map(plantRealm));
            }
        }

        ArrayList<Irrigation> irrigations = new ArrayList<>();

        // add irrigations
        if(gardenRealm.getIrrigations() != null) {
            for (IrrigationRealm irrigationRealm : gardenRealm.getIrrigations()) {
                Irrigation irrigation = toIrrigation.map(irrigationRealm);
                irrigation.setDose(toDose.map(irrigationRealm.getDose()));
                irrigations.add(irrigation);
            }
        }

        garden.setIrrigations(irrigations);

        return garden;
    }

}
