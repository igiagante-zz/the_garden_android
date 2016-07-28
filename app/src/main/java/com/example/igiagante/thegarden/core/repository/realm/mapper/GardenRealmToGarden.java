package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealmToGarden implements Mapper<GardenRealm, Garden> {

    private final PlantRealmToPlant toPlant;
    private final IrrigationRealmToIrrigation toIrrigation;
    private final DoseRealmToDose toDose;
    private final NutrientRealmToNutrient toNutrient;

    public GardenRealmToGarden(@NonNull Context context) {
        this.toPlant = new PlantRealmToPlant(context);
        this.toIrrigation = new IrrigationRealmToIrrigation();
        this.toDose = new DoseRealmToDose();
        this.toNutrient = new NutrientRealmToNutrient();
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
                DoseRealm doseRealm = irrigationRealm.getDose();
                Dose dose = toDose.map(doseRealm);
                addNutrients(doseRealm, dose);
                irrigation.setDose(dose);
                irrigations.add(irrigation);
            }
        }

        garden.setIrrigations(irrigations);

        return garden;
    }

    private void addNutrients(DoseRealm doseRealm, Dose dose){

        RealmList<NutrientRealm> nutrientRealms = doseRealm.getNutrients();
        ArrayList<Nutrient> nutrients = new ArrayList<>();

        for(NutrientRealm nutrientRealm : nutrientRealms) {
            nutrients.add(toNutrient.map(nutrientRealm));
        }

        dose.setNutrients(nutrients);
    }
}
