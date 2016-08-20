package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.services.SensorTempApi;

import java.util.List;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
public class RestApiSensorTempRepository implements Repository<SensorTemp> {

    private final SensorTempApi api;

    public RestApiSensorTempRepository() {
        this.api = ServiceFactory.createRetrofitService(SensorTempApi.class);
    }

    @Override
    public Observable<SensorTemp> getById(String id) {
        return null;
    }

    @Override
    public Observable<SensorTemp> getByName(String name) {
        return null;
    }

    @Override
    public Observable<SensorTemp> add(SensorTemp item) {
        return null;
    }

    @Override
    public Observable<Integer> add(Iterable<SensorTemp> items) {
        return null;
    }

    @Override
    public Observable<SensorTemp> update(SensorTemp item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(String id) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<SensorTemp>> query(Specification specification) {
        return api.getValues();
    }
}
