package com.example.igiagante.thegarden.plants.repository;

import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;

import java.util.List;

import rx.Observable;

/**
 * Created by igiagante on 19/4/16.
 */
public class RestAPIRepository implements Repository<Plant> {

    @Override
    public void add(Plant item) {

    }

    @Override
    public void add(Iterable<Plant> items) {

    }

    @Override
    public void update(Plant item) {

    }

    @Override
    public void remove(Plant item) {

    }

    @Override
    public void remove(Specification specification) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Plant>> query(Specification specification) {
        return null;
    }
}
