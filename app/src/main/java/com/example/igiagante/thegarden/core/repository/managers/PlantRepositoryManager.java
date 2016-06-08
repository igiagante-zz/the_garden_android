package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.PlantByNameSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.RestApiPlantRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public class PlantRepositoryManager extends RepositoryManager<Repository<Plant>> {

    @Inject
    public PlantRepositoryManager(Context context) {
        mRepositories.add(new PlantRealmRepository(context));
        mRepositories.add(new RestApiPlantRepository(context));
    }

    public Observable<String> add(@NonNull Plant plant) {

        // search a plant using the name
        Specification plantSpecification = new PlantByNameSpecification(plant.getName());

        // there should be only one plant with this name, so it will ask for the list's item
        Observable<Plant> observableOne = mRepositories.get(0).query(plantSpecification)
                .flatMap(list -> Observable.just(list.get(0)));

        // check if the plant already exits. If the plant exists, it returns the plant. On the other
        // side, it asks to the rest api to save the plant.
        return observableOne.map(v -> true).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observableOne.map(plant1 -> plant.getId())
                        : mRepositories.get(1).add(plant));
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        return mRepositories.get(0).query(specification)
                .map(v -> true).firstOrDefault(false)
                .flatMap(exists -> exists ? mRepositories.get(0).query(specification)
                        : mRepositories.get(1).query(specification));
    }
}
