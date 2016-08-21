package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.plant.PlantByNameSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiPlantRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public class PlantRepositoryManager extends RepositoryManager<Repository<Plant>> {

    @Inject
    public PlantRepositoryManager(Context context, Session session) {
        super(context);
        mRepositories.add(new PlantRealmRepository(context));
        mRepositories.add(new RestApiPlantRepository(context, session));
    }

    public Observable<Plant> add(@NonNull Plant plant) {

        // search a plant using the name
        Specification plantSpecification = new PlantByNameSpecification(plant.getName());

        // there should be only one plant with this name, so it will ask for the list's item
        Observable<Plant> observableOne = mRepositories.get(0).query(plantSpecification)
                .flatMap(list -> Observable.just(list.get(0)));

        List<Plant> list = new ArrayList<>();
        observableOne.map(plant1 -> list.add(plant));

        if(!checkInternet()) {
            return Observable.just(list.get(0));
        }

        Observable<List<Plant>> observable = Observable.just(list);

        // check if the plant already exits. If the plant exists, it returns the plant. On the other
        // side, it asks to the rest api to save the plant.
        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observableOne
                        : mRepositories.get(1).add(plant));
    }

    public Observable<Plant> update(@NonNull Plant plant) {
        if(!checkInternet()){
            return Observable.just(plant);
        }
        return mRepositories.get(1).update(plant);
    }

    public Observable delete(@NonNull String plantId) {

        if(!checkInternet()) {
            return Observable.just(-1);
        }

        // delete plant from api
        Observable<Integer> resultFromApi = mRepositories.get(1).remove(plantId);

        //Create a list of Integer in order to avoid calling Realm from other Thread
        List<Integer> list = new ArrayList<>();
        resultFromApi.subscribeOn(Schedulers.io()).toBlocking().subscribe(success -> list.add(success));

        // delete plant from DB
        if(!list.isEmpty() && list.get(0) != -1) {
            Observable<Integer> resultFromDB = mRepositories.get(0).remove(plantId);
            resultFromDB.toBlocking().subscribe(success -> list.add(success));
        }

        Observable<Integer> result;

        if(list.contains(-1)) {
            result = Observable.just(-1);
        } else {
            result = Observable.from(list);
        }

        return result;
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        Observable<List<Plant>> query = mRepositories.get(0).query(specification);

        List<Plant> list = new ArrayList<>();
        query.subscribe(plants -> list.addAll(plants));

        Observable<List<Plant>> observable = Observable.just(list);

        if (!checkInternet()) {
            return Observable.just(list.get(0));
        }

        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observable
                        : mRepositories.get(1).query(null));
    }
}
