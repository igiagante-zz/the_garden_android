package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.services.AttributeRestApi;

import java.util.List;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class RestApiAttributeRepository implements Repository<Attribute> {

    private final AttributeRestApi api;

    public RestApiAttributeRepository() {

        this.api = ServiceFactory.createRetrofitService(AttributeRestApi.class);
    }

    @Override
    public Observable<Attribute> getById(String id) {
        return null;
    }

    @Override
    public Observable<Attribute> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Attribute> add(Attribute item) {
        return null;
    }

    @Override
    public Observable<Integer> add(Iterable<Attribute> items) {
        return null;
    }

    @Override
    public Observable<Attribute> update(Attribute item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(String attributeId) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Attribute>> query(Specification specification) {
        Observable<List<Attribute>> attributes = api.getAttributes();
        return attributes;
    }
}
