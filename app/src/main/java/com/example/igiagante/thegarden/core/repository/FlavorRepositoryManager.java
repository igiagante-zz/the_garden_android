package com.example.igiagante.thegarden.core.repository;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.restAPI.RestApiFlavorRepository;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDaoRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class FlavorRepositoryManager {

    private FlavorDaoRepository flavorDaoRepository;
    private RestApiFlavorRepository restApiFlavorRepository;

    @Inject
    public FlavorRepositoryManager(@NonNull FlavorDaoRepository flavorDaoRepository,
                                   @NonNull RestApiFlavorRepository restApiFlavorRepository){
        this.flavorDaoRepository = flavorDaoRepository;
        this.restApiFlavorRepository = restApiFlavorRepository;
    }

    /**
     * Return a resource using the id
     * @param id Object id
     * @return Observable
     */
    public Observable getById(String id) {

        final Observable observable = flavorDaoRepository.getById(id);

        observable.map(flavor -> {
            if(flavor == null) {
                return restApiFlavorRepository.getById(id);
            }
           return Observable.just(flavor);
        });

        return observable;
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        final Observable observable = flavorDaoRepository.query(specification);

        observable.map(listOfFlavors -> {

            if(((List)listOfFlavors).isEmpty()) {
                return restApiFlavorRepository.query(specification);
            }
            return Observable.just(listOfFlavors);
        });

        return observable;
    }
}
