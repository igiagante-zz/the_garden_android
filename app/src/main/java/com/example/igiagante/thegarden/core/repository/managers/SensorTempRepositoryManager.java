package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.realm.SensorTempRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.SensorTempSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiSensorTempRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempRepositoryManager extends BaseRepositoryManager {

    private SensorTempRealmRepository realmRepository;
    private RestApiSensorTempRepository api;

    @Inject
    public SensorTempRepositoryManager(Context context) {
        super(context);
        realmRepository = new SensorTempRealmRepository(context);
        api = new RestApiSensorTempRepository();
    }

    public Observable getSensorData() {

        // if it does not have internet connection, lets use the DB
        if(!checkInternet()) {
            SensorTempSpecification sensorTempSpecification = new SensorTempSpecification();
            Observable<List<SensorTemp>> query = realmRepository.query(sensorTempSpecification);

            List<SensorTemp> list = new ArrayList<>();
            query.subscribe(sensorTemps -> list.addAll(sensorTemps));

            return Observable.just(list);
        } else {
            return api.query(null);
        }
    }
}
