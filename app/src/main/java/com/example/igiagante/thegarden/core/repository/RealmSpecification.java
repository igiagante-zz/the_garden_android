package com.example.igiagante.thegarden.core.repository;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 *  @author Ignacio Giagante, on 5/5/16.
 */
public interface RealmSpecification<T extends RealmObject> extends Specification {

    Observable<RealmResults<T>> toObservableRealmResults(Realm realm);
    RealmResults<T> toRealmResults(Realm realm);
    T toObjectRealm(Realm realm);
}
