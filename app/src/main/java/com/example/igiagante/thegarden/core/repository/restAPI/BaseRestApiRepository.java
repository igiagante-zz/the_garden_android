package com.example.igiagante.thegarden.core.repository.restAPI;

import android.content.Context;

import com.example.igiagante.thegarden.core.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class BaseRestApiRepository<T> {

    protected Context mContext;

    public BaseRestApiRepository(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Go to the api and then continue with the DB
     * @param apiResult after call api
     * @param item the item to be persisted or updated
     * @param repository DB
     */
    protected T execute(Observable<T> apiResult, T item, Class repository) {

        // get Data From api
        List<T> listOne = new ArrayList<>();
        apiResult.subscribeOn(Schedulers.io()).toBlocking().subscribe(garden1 -> listOne.add(garden1));

        // update the garden into database
        Repository dataBase = null;
        try {
            dataBase = (Repository) repository.newInstance();
        } catch (IllegalAccessException e) {

        } catch (InstantiationException ei) {

        }

        Observable<T> dbResult = dataBase.update(listOne.get(0));

        List<T> list = new ArrayList<>();
        dbResult.toBlocking().subscribe(gardenId -> list.add(gardenId));

        return list.get(0);
    }
}
