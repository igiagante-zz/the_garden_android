package com.example.igiagante.thegarden.repositoryImpl.realm.specification;

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
    public Observable<RealmResults<PlantRealm>> toRealmResults(Realm realm) {
        return realm.where(PlantRealm.class)
                .findAllAsync().asObservable();
    }
}
