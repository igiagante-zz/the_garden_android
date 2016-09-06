package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenToGardenRealm implements Mapper<Garden, GardenRealm> {

    private final Realm realm;
    private final PlantToPlantRealm toPlantRealm;
    private final IrrigationToIrrigationRealm toIrrigationRealm;

    public GardenToGardenRealm(Realm realm) {
        this.realm = realm;
        this.toPlantRealm = new PlantToPlantRealm(realm);
        this.toIrrigationRealm = new IrrigationToIrrigationRealm(realm);
    }

    @Override
    public GardenRealm map(Garden garden) {
        GardenRealm gardenRealm = realm.createObject(GardenRealm.class);
        gardenRealm.setId(garden.getId());
        return copy(garden, gardenRealm);
    }

    @Override
    public GardenRealm copy(Garden garden, GardenRealm gardenRealm) {

        gardenRealm.setUserId(garden.getUserId());
        gardenRealm.setName(garden.getName());
        gardenRealm.setStartDate(garden.getStartDate());
        gardenRealm.setEndDate(garden.getEndDate());

        // plants realm list
        RealmList<PlantRealm> plantRealms = new RealmList<>();

        // add plants
        if (garden.getPlants() != null) {
            for (Plant plant : garden.getPlants()) {
                PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(Table.ID, plant.getId()).findFirst();
                if (plantRealm == null) {
                    // create plant realm object and set id
                    plantRealm = realm.createObject(PlantRealm.class);
                    plantRealm.setId(plant.getId());
                }
                plantRealms.add(toPlantRealm.copy(plant, plantRealm));
            }
        }

        gardenRealm.setPlants(plantRealms);

        // irrigations realm list
        RealmList<IrrigationRealm> irrigationRealms = new RealmList<>();

        // add irrigations
        if (garden.getIrrigations() != null) {
            for (Irrigation irrigation : garden.getIrrigations()) {
                IrrigationRealm irrigationRealm = realm.where(IrrigationRealm.class).equalTo(Table.ID, irrigation.getId()).findFirst();
                if (irrigationRealm == null) {
                    // create irrigation realm object and set id
                    irrigationRealm = realm.createObject(IrrigationRealm.class);
                    irrigationRealm.setId(irrigation.getId());
                }
                irrigationRealms.add(toIrrigationRealm.copy(irrigation, irrigationRealm));
            }
        }

        gardenRealm.setIrrigations(irrigationRealms);

        return gardenRealm;
    }
}
