package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.garden.GardenByNameSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiGardenRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRepositoryManager extends RepositoryManager<Repository<Garden>> {

    private Context context;
    private Session session;

    @Inject
    public GardenRepositoryManager(Context context, Session session) {
        super(context);
        this.context = context;
        this.session = session;
        mRepositories.add(new GardenRealmRepository(context));
        mRepositories.add(new RestApiGardenRepository(context, session));
    }

    public Observable add(@NonNull Garden garden) {

        // search a garden using the name
        Specification gardenByNameSpecification = new GardenByNameSpecification(garden.getName());

        // there should be only one garden with this name, so it will ask for the list's item
        Observable<Garden> observableOne = mRepositories.get(0).query(gardenByNameSpecification)
                .flatMap(list -> Observable.just(list.get(0)));

        List<Garden> list = new ArrayList<>();
        observableOne.map(plant1 -> list.add(garden));

        Observable<List<Garden>> observable = Observable.just(list);

        if (!checkInternet()) {
            return Observable.just(list.get(0));
        }

        // check if the garden already exits. If the garden exists, it returns the garden. On the other
        // side, it asks to the rest api to save the garden.
        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observableOne
                        : mRepositories.get(1).add(garden));
    }

    public Observable update(@NonNull Garden garden) {
        if (!checkInternet()) {
            return Observable.just(garden);
        }
        return mRepositories.get(1).update(garden);
    }

    public Observable delete(@NonNull String gardenId, @NonNull String userId) {

        if (!checkInternet()) {
            return Observable.just(-1);
        }

        // TODO - Refactor this
        RestApiGardenRepository restApiGardenRepository = new RestApiGardenRepository(context, session);

        // delete plant from api
        Observable<Integer> resultFromApi = restApiGardenRepository.remove(gardenId, userId);

        //Create a list of Integer in order to avoid calling Realm from other Thread
        List<Integer> list = new ArrayList<>();
        resultFromApi.subscribeOn(Schedulers.io()).toBlocking().subscribe(success -> list.add(success));

        // delete plant from DB
        if (!list.isEmpty() && list.get(0) != -1) {
            Observable<Integer> resultFromDB = mRepositories.get(0).remove(gardenId);
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

    public Observable existGardenByNameAndUserId(Garden garden) {

        GardenRealmRepository gardenRealmRepository = new GardenRealmRepository(context);

        Observable<Garden> gardenObservable = gardenRealmRepository
                .getByNameAndUserId(garden.getName(), garden.getUserId());

        List<Boolean> list = new ArrayList<>();
        gardenObservable.subscribe(nutrient -> {
            if (nutrient != null) {
                list.add(Boolean.TRUE);
            } else {
                list.add(Boolean.FALSE);
            }
        });

        return list.isEmpty() ? Observable.just(Boolean.FALSE) : Observable.just(list.get(0));
    }

    /**
     * Return an observable a list of resources.
     *
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        Observable<List<Garden>> query = mRepositories.get(0).query(specification);

        List<Garden> list = new ArrayList<>();
        query.subscribe(gardens -> list.addAll(gardens));

        if (!checkInternet()) {
            return Observable.just(list);
        }

        Observable<List<Garden>> observable = Observable.just(list);

        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observable
                        : mRepositories.get(1).query(null));
    }
}
