package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.SensorTempRealm;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempRealmToSensorTemp implements Mapper<SensorTempRealm, SensorTemp> {

    @Override
    public SensorTemp map(SensorTempRealm sensorTempRealm) {
        SensorTemp sensorTemp = new SensorTemp();
        return copy(sensorTempRealm, sensorTemp);
    }

    @Override
    public SensorTemp copy(SensorTempRealm sensorTempRealm, SensorTemp sensorTemp) {
        sensorTemp.setDate(sensorTempRealm.getDate());
        sensorTemp.setTemp(sensorTempRealm.getTemp());
        sensorTemp.setHumidity(sensorTempRealm.getHumidity());
        return sensorTemp;
    }
}
