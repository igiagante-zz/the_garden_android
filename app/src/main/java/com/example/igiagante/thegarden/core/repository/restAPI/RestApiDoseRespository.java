package com.example.igiagante.thegarden.core.repository.restAPI;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.service.DoseRestApi;

import java.util.List;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class RestApiDoseRespository extends BaseRestApiRepository<Dose> implements Repository<Dose> {

    private final DoseRestApi api;

    public RestApiDoseRespository(Context context) {
        super(context);
        api = ServiceFactory.createRetrofitService(DoseRestApi.class);
    }

    @Override
    public Observable<Dose> getById(String id) {
        return api.getDose(id);
    }

    @Override
    public Observable<Dose> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Dose> add(Dose item) {
        return null;
    }

    @Override
    public Observable<Integer> add(Iterable<Dose> items) {
        return null;
    }

    @Override
    public Observable<Dose> update(Dose item) {
        return null;
    }


    @Override
    public Observable<Integer> remove(String id) {
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
    public Observable<List<Dose>> query(Specification specification) {
        return null;
    }
}
