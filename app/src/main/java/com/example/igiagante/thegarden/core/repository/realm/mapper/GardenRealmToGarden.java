package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealmToGarden implements Mapper<Garden, GardenRealm> {

    private final Realm realm;

    public GardenRealmToGarden(Realm realm) {
        this.realm = realm;
    }

    @Override
    public GardenRealm map(Garden garden) {

        GardenRealm gardenRealm = realm.createObject(GardenRealm.class);
        gardenRealm.setId(garden.getId());
        return copy(garden, gardenRealm);
    }

    @Override
    public GardenRealm copy(Garden garden, GardenRealm gardenRealm) {
        gardenRealm.setName(garden.getName());
        gardenRealm.setStartDate(garden.getStartDate());
        gardenRealm.setEndDate(garden.getEndDate());
        return gardenRealm;
    }
}
