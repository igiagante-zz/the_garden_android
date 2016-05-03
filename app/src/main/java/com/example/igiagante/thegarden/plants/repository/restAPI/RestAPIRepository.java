package com.example.igiagante.thegarden.plants.repository.restAPI;

import com.example.igiagante.thegarden.core.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.restAPI.service.PlantRestAPI;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by igiagante on 19/4/16.
 */
@Singleton
public class RestAPIRepository implements Repository<Plant> {

    private final PlantRestAPI api;

    @Inject
    public RestAPIRepository() {
        api = ServiceFactory.createRetrofitService(PlantRestAPI.class);
    }

    @Override
    public Observable<Plant> getById(String id) {
        return api.getPlant(id);
    }

    @Override
    public void add(Plant plant) {

    }

    @Override
    public void add(Iterable<Plant> plants) {

    }

    @Override
    public void update(Plant plant) {

    }

    @Override
    public void remove(Plant plant) {

    }

    @Override
    public void remove(Specification specification) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Plant>> query(Specification specification) {
        return api.getPlants();
    }
}
