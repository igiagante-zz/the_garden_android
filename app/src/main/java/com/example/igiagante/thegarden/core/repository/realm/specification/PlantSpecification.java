package com.example.igiagante.thegarden.core.repository.realm.specification;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author giagante on 5/5/16.
 */
public class PlantSpecification implements RealmSpecification {

    @Override
    public Observable<RealmResults<PlantRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(PlantRealm.class).findAll().asObservable();
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(PlantRealm.class).findAll();
    }

    @Override
    public PlantRealm toPlantRealm(@NonNull Realm realm) {
        return null;
    }
}
