package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.RestApiGardenRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRepositoryManager extends RepositoryManager<Repository<Garden>> {

    @Inject
    public GardenRepositoryManager(Context context) {
        mRepositories.add(new GardenRealmRepository(context));
        mRepositories.add(new RestApiGardenRepository(context));
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        Observable<List<Garden>> query = mRepositories.get(0).query(specification);

        List<Garden> list = new ArrayList<>();
        query.subscribe(gardens -> list.addAll(gardens));

        Observable<List<Garden>> observable = Observable.just(list);

        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observable
                        : mRepositories.get(1).query(null));
    }
}
