package com.example.igiagante.thegarden.core.repository.restAPI;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.service.PlagueRestApi;

import java.util.List;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public class RestApiPlagueRepository implements Repository<Plague> {

    private final PlagueRestApi api;

    public RestApiPlagueRepository() {
        this.api = ServiceFactory.createRetrofitService(PlagueRestApi.class);
    }

    @Override
    public Observable<Plague> getById(String id) {
        return null;
    }

    @Override
    public Observable<Plague> getByName(String name) {
        return null;
    }

    @Override
    public Observable<String> add(Plague item) {
        return null;
    }

    @Override
    public Observable<Integer> add(Iterable<Plague> items) {
        return null;
    }

    @Override
    public Observable<Plague> update(Plague item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(String plagueId) {
        return null;
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Plague>> query(Specification specification) {
        return api.getPlagues();
    }
}
