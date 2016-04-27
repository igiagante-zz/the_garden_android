package com.example.igiagante.thegarden.repositoryImpl.realm.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.PlantTable;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by igiagante on 27/4/16.
 */
public class PlantByNameSpecification implements RealmSpecification {

    private final String name;

    public PlantByNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Observable<RealmResults<PlantRealm>> toRealmResults(Realm realm) {
        return realm.where(PlantRealm.class)
                .equalTo(PlantTable.NAME, name)
                .findAll().asObservable();
    }
}
