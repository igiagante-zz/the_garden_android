package com.example.igiagante.thegarden.plants.repository.realm.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.PlantTable;

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
