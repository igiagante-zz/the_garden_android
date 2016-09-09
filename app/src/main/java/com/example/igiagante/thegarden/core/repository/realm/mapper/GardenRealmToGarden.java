package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealmToGarden implements Mapper<GardenRealm, Garden> {

    private final PlantRealmToPlant toPlant;
    private final IrrigationRealmToIrrigation toIrrigation;
    private final DoseRealmToDose toDose;
    private final NutrientPerDoseRealmToNutrient toNutrient;

    public GardenRealmToGarden(@NonNull Context context) {
        this.toPlant = new PlantRealmToPlant(context);
        this.toIrrigation = new IrrigationRealmToIrrigation();
        this.toDose = new DoseRealmToDose();
        this.toNutrient = new NutrientPerDoseRealmToNutrient();
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
        garden.setUserId(gardenRealm.getUserId());
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

        garden.setPlants(plants);

        ArrayList<Irrigation> irrigations = new ArrayList<>();

        // add irrigations
        if(gardenRealm.getIrrigations() != null) {
            for (IrrigationRealm irrigationRealm : gardenRealm.getIrrigations()) {
                Irrigation irrigation = toIrrigation.map(irrigationRealm);
                DoseRealm doseRealm = irrigationRealm.getDose();
                Dose dose = toDose.map(doseRealm);
                irrigation.setDose(dose);
                irrigations.add(irrigation);
            }
        }

        garden.setIrrigations(irrigations);

        return garden;
    }
}
