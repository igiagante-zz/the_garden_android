package com.example.igiagante.thegarden.plants.repository.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by ignaciogiagante on 4/26/16.
 */
public class PlantSpecification implements RealmSpecification {

    @Override
    public Observable<RealmResults<PlantRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(PlantRealm.class).findAll().asObservable();
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(Realm realm) {
        return realm.where(PlantRealm.class).findAll();
    }

    @Override
    public PlantRealm toPlantRealm(Realm realm) {
        return null;
    }
}
