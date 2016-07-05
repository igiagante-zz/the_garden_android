package com.example.igiagante.thegarden.core.repository.restAPI;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.repository.Repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class BaseRestApiRepository<T> {

    private static final String TAG = BaseRestApiRepository.class.getSimpleName();

    protected Context mContext;

    public BaseRestApiRepository(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Go to the api and then continue with the DB
     * @param apiResult after call api
     * @param item the item to be persisted or updated
     * @param repository DB
     * @param update indicate if the transaction is about an updating
     */
    protected T execute(Observable<T> apiResult, T item, Class repository, boolean update) {

        // get Data From api
        List<T> listOne = new ArrayList<>();
        apiResult.subscribeOn(Schedulers.io()).toBlocking().subscribe(garden1 -> listOne.add(garden1));

        // update the garden into database
        Repository dataBase = null;

        try {
            Constructor<?> cons = repository.getConstructor(Context.class);
            dataBase = (Repository) cons.newInstance(mContext);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException ei) {
            Log.e(TAG, ei.getMessage());
            ei.printStackTrace();
        }

        List<T> list = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        if(update) {
            Observable<T> dbResult = dataBase.update(listOne.get(0));
            dbResult.toBlocking().subscribe(gardenId -> list.add(gardenId));
        } else {
            Observable<String> result = dataBase.add(listOne.get(0));
            result.toBlocking().subscribe(gardenId -> ids.add(gardenId));
        }

        return list.get(0);
    }
}
