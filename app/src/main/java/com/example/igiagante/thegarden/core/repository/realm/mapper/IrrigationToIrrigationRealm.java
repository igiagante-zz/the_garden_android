package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class IrrigationToIrrigationRealm implements Mapper<Irrigation, IrrigationRealm> {

    private final Realm realm;

    public IrrigationToIrrigationRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public IrrigationRealm map(Irrigation irrigation) {
        IrrigationRealm irrigationRealm = realm.createObject(IrrigationRealm.class);
        irrigationRealm.setId(irrigation.getId());
        return copy(irrigation, irrigationRealm);
    }

    @Override
    public IrrigationRealm copy(Irrigation irrigation, IrrigationRealm irrigationRealm) {

        irrigationRealm.setDoseId(irrigation.getDoseId());
        irrigationRealm.setIrrigationDate(irrigation.getIrrigationDate());
        irrigationRealm.setGardenId(irrigation.getGardenId());
        irrigationRealm.setQuantity(irrigation.getQuantity());

        return irrigationRealm;
    }
}
