package com.example.igiagante.thegarden.core.repository.realm.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.SensorTempRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempSpecification implements RealmSpecification<SensorTempRealm> {

    @Override
    public Observable<RealmResults<SensorTempRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(SensorTempRealm.class).findAll().asObservable();
    }

    @Override
    public RealmResults<SensorTempRealm> toRealmResults(Realm realm) {
        return realm.where(SensorTempRealm.class).findAll();
    }
}
