package com.example.igiagante.thegarden.core.repository.sqlite;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 9/6/16.
 */
public interface ConvertRealmResultToModel<RealmType extends RealmObject, Model> {

    Observable<List<Model>> toObservableModel(Observable<RealmResults<RealmType>> realmResults);
}
