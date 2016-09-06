package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class IrrigationToIrrigationRealm implements Mapper<Irrigation, IrrigationRealm> {

    private final Realm realm;
    private final DoseToDoseRealm toDoseRealm;

    public IrrigationToIrrigationRealm(Realm realm) {
        this.realm = realm;
        this.toDoseRealm = new DoseToDoseRealm(realm);
    }

    @Override
    public IrrigationRealm map(Irrigation irrigation) {
        IrrigationRealm irrigationRealm = realm.createObject(IrrigationRealm.class);
        irrigationRealm.setId(irrigation.getId());
        return copy(irrigation, irrigationRealm);
    }

    @Override
    public IrrigationRealm copy(Irrigation irrigation, IrrigationRealm irrigationRealm) {

        irrigationRealm.setIrrigationDate(irrigation.getIrrigationDate());
        irrigationRealm.setGardenId(irrigation.getGardenId());
        irrigationRealm.setQuantity(irrigation.getQuantity());

        DoseRealm doseRealm = realm.where(DoseRealm.class).equalTo(Table.ID,
                irrigation.getDose().getId()).findFirst();
        if (doseRealm == null) {
            // create dose realm object and set id
            doseRealm = realm.createObject(DoseRealm.class);
            doseRealm.setId(irrigation.getDose().getId());
        }
        irrigationRealm.setDose(toDoseRealm.copy(irrigation.getDose(), doseRealm));

        return irrigationRealm;
    }
}
