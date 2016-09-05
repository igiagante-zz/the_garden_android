package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.IrrigationRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiIrrigationRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public class IrrigationRepositoryManager extends RepositoryManager<Repository<Irrigation>> {

    @Inject
    public IrrigationRepositoryManager(Context context, Session session) {
        super(context);
        mRepositories.add(new IrrigationRealmRepository(context));
        mRepositories.add(new RestApiIrrigationRepository(context, session));
    }

    public Observable add(@NonNull Irrigation irrigation) {
        if (!checkInternet()) {
            return Observable.just(irrigation);
        }
        return mRepositories.get(1).add(irrigation);
    }

    public Observable update(@NonNull Irrigation irrigation) {
        if (!checkInternet()) {
            return Observable.just(irrigation);
        }
        return mRepositories.get(1).update(irrigation);
    }

    public Observable delete(@NonNull String irrigationId) {

        if (!checkInternet()) {
            return Observable.just(-1);
        }

        // delete plant from api
        Observable<Integer> resultFromApi = mRepositories.get(1).remove(irrigationId);

        //Create a list of Integer in order to avoid calling Realm from other Thread
        List<Integer> list = new ArrayList<>();
        resultFromApi.subscribeOn(Schedulers.io()).toBlocking().subscribe(success -> list.add(success));

        // delete plant from DB
        if(!list.isEmpty() && list.get(0) != -1) {
            Observable<Integer> resultFromDB = mRepositories.get(0).remove(irrigationId);
            resultFromDB.toBlocking().subscribe(success -> list.add(success));
        }

        Observable<Integer> result;

        if(list.contains(-1)) {
            result = Observable.just(-1);
        } else {
            result = Observable.just(1);
        }

        return result;
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(Specification specification) {

        Observable<List<Irrigation>> query = mRepositories.get(0).query(specification);

        List<Irrigation> list = new ArrayList<>();
        query.subscribe(irrigations -> list.addAll(irrigations));

        Observable<List<Irrigation>> observable = Observable.just(list);

        if (!checkInternet()) {
            return Observable.just(list.get(0));
        }

        return observable.map(v -> !v.isEmpty()).firstOrDefault(false)
                .flatMap(exists -> exists
                        ? observable
                        : mRepositories.get(1).query(null));
    }
}
