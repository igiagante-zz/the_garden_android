package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class IrrigationRealmToIrrigation implements Mapper<IrrigationRealm, Irrigation> {

    @Override
    public Irrigation map(IrrigationRealm irrigationRealm) {
        Irrigation irrigation = new Irrigation();
        irrigation.setId(irrigationRealm.getId());
        return copy(irrigationRealm, irrigation);
    }

    @Override
    public Irrigation copy(IrrigationRealm irrigationRealm, Irrigation irrigation) {

        irrigation.setDoseId(irrigationRealm.getDoseId());
        irrigation.setGardenId(irrigationRealm.getGardenId());
        irrigation.setIrrigationDate(irrigationRealm.getIrrigationDate());
        irrigation.setQuantity(irrigationRealm.getQuantity());

        return irrigation;
    }
}
