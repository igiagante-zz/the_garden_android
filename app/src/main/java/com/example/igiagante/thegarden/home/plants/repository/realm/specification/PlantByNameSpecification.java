package com.example.igiagante.thegarden.home.plants.repository.realm.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.home.plants.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.home.plants.repository.realm.modelRealm.PlantTable;

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
    public Observable<RealmResults<PlantRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(PlantRealm.class)
                .equalTo(PlantTable.NAME, name)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(Realm realm) {
        return null;
    }

    @Override
    public PlantRealm toPlantRealm(Realm realm) {
        return realm.where(PlantRealm.class).equalTo(PlantTable.NAME, name).findFirst();
    }
}
