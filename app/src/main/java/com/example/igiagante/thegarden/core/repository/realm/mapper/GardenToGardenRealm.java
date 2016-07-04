package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenToGardenRealm implements Mapper<GardenRealm, Garden> {

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
        return garden;
    }
}
