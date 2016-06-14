package com.example.igiagante.thegarden.core.repository.restAPI;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.service.AttributeRestApi;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public class RestApiAttributeRepository implements Repository<Attribute> {

    private final AttributeRestApi api;
    private Context mContext;

    public RestApiAttributeRepository(Context context) {
        this.mContext = context;
        this.api = ServiceFactory.createRetrofitService(AttributeRestApi.class);
    }

    @Override
    public Observable<Attribute> getById(String id) {
        return null;
    }

    @Override
    public Observable<String> add(Attribute item) {
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
    public Observable<Integer> remove(Attribute item) {
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
    public Observable<List<Attribute>> query(Specification specification) {
        return api.getAttributes();
    }
}
