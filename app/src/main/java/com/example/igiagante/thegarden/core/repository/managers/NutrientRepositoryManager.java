package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.NutrientRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.nutrient.NutrientByNameSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiNutrientRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientRepositoryManager extends RepositoryManager<Repository<Nutrient>> {

    @Inject
    public NutrientRepositoryManager(Context context, Session session) {
        super(context);
        mRepositories.add(new NutrientRealmRepository(context));
        mRepositories.add(new RestApiNutrientRepository(context, session));
    }

    public Observable add(@NonNull Nutrient nutrient) {

        // search a nutrient using the name
        Specification plantSpecification = new NutrientByNameSpecification(nutrient.getName());

        // there should be only one nutrient with this name, so it will ask for the list's item
        Observable<Nutrient> observableOne = mRepositories.get(0).query(plantSpecification)
                .flatMap(list -> Observable.just(list.get(0)));

        List<Nutrient> list = new ArrayList<>();
        observableOne.map(plant1 -> list.add(nutrient));

        Observable<List<Nutrient>> observable = Observable.just(list);

        if (!checkInternet()) {
            return Observable.just(list.get(0));
        }

        // check if the nutrient already exits. If the nutrient exists, it returns the nutrient. On the other
        // side, it asks to the rest api to save the nutrient.
        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observableOne
                        : mRepositories.get(1).add(nutrient));
    }

    public Observable update(@NonNull Nutrient nutrient) {
        if (!checkInternet()) {
            return Observable.just(nutrient);
        }
        return mRepositories.get(1).update(nutrient);
    }

    public Observable delete(@NonNull String nutrientId) {

        if (!checkInternet()) {
            return Observable.just(-1);
        }

        // delete plant from api
        Observable<Integer> resultFromApi = mRepositories.get(1).remove(nutrientId);

        //Create a list of Integer in order to avoid calling Realm from other Thread
        List<Integer> list = new ArrayList<>();
        resultFromApi.subscribeOn(Schedulers.io()).toBlocking().subscribe(success -> list.add(success));

        // delete plant from DB
        if (!list.isEmpty() && list.get(0) != -1) {
            Observable<Integer> resultFromDB = mRepositories.get(0).remove(nutrientId);
            resultFromDB.toBlocking().subscribe(success -> list.add(success));
        }

        Observable<Integer> result;

        if (list.contains(-1)) {
            result = Observable.just(-1);
        } else {
            result = Observable.just(1);
        }

        return result;
    }

    /**
     * Return an observable a list of resources.
     *
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        Observable<List<Nutrient>> query = mRepositories.get(0).query(specification);

        List<Nutrient> list = new ArrayList<>();
        query.subscribe(nutrients -> list.addAll(nutrients));

        Observable<List<Nutrient>> observable = Observable.just(list);

        if (!checkInternet()) {
            return Observable.just(list);
        }

        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observable
                        : mRepositories.get(1).query(null));
    }
}
