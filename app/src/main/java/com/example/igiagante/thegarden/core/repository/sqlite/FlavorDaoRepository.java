package com.example.igiagante.thegarden.core.repository.sqlite;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class FlavorDaoRepository implements Repository<Flavor> {

    private FlavorDao flavorDao;

    @Inject
    public FlavorDaoRepository(@Named("flavorDao") FlavorDao flavorDao) {
        this.flavorDao = flavorDao;
    }

    @Override
    public Observable<Flavor> getById(String id) {
        return Observable.just(flavorDao.getFlavor(id));
    }

    @Override
    public Observable<Flavor> add(Flavor item) {
        return null;
    }

    @Override
    public Observable<Flavor> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Integer> add(Iterable<Flavor> items) {
        return null;
    }

    @Override
    public Observable<Flavor> update(Flavor item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(String flavorId) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Flavor>> query(Specification specification) {
        return Observable.just(flavorDao.getFlavors());
    }
}
