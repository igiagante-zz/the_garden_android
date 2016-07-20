package com.example.igiagante.thegarden.core.repository.realm.specification.garden;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenSpecification implements RealmSpecification<GardenRealm> {

    @Override
    public Observable<RealmResults<GardenRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(GardenRealm.class).findAll().asObservable();
    }

    @Override
    public RealmResults<GardenRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(GardenRealm.class).findAll();
    }
}
