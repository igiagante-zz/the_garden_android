package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.SensorTempRealm;

import java.util.UUID;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempToSensorTempRealm implements Mapper<SensorTemp, SensorTempRealm> {

    private final Realm realm;

    public SensorTempToSensorTempRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public SensorTempRealm map(SensorTemp sensorTemp) {
        SensorTempRealm sensorTempRealm = realm.createObject(SensorTempRealm.class);
        sensorTempRealm.setId(UUID.randomUUID().toString());
        copy(sensorTemp, sensorTempRealm);

        return sensorTempRealm;
    }

    @Override
    public SensorTempRealm copy(SensorTemp sensorTemp, SensorTempRealm sensorTempRealm) {
        sensorTempRealm.setDate(sensorTemp.getDate());
        sensorTempRealm.setTemp(sensorTemp.getTemp());
        sensorTempRealm.setHumidity(sensorTemp.getHumidity());
        return sensorTempRealm;
    }
}
