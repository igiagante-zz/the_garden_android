package com.example.igiagante.thegarden.core.repository;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by igiagante on 26/4/16.
 */
public interface RealmSpecification<T extends RealmObject> extends Specification {

    Observable<RealmResults<T>> toObservableRealmResults(Realm realm);
    RealmResults<T> toRealmResults(Realm realm);
    T toPlantRealm(Realm realm);
}
