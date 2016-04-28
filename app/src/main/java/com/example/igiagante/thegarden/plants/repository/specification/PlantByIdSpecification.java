package com.example.igiagante.thegarden.plants.repository.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.PlantTable;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by igiagante on 26/4/16.
 */
public class PlantByIdSpecification implements RealmSpecification {

    private final String id;

    public PlantByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Observable<RealmResults<PlantRealm>> toObservableRealmResults(Realm realm) {

        return realm.where(PlantRealm.class)
                .equalTo(PlantTable.ID, id)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(Realm realm) {
        return null;
    }

    @Override
    public PlantRealm toPlantRealm(Realm realm) {
        return null;
    }
}
