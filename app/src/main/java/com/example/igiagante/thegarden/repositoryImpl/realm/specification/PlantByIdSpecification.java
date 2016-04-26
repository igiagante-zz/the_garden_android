package com.example.igiagante.thegarden.repositoryImpl.realm.specification;

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

    private final int id;

    public PlantByIdSpecification(final int id) {
        this.id = id;
    }

    @Override
    public Observable<RealmResults<PlantRealm>> toRealmResults(Realm realm) {

        return realm.where(PlantRealm.class)
                .equalTo(PlantTable.ID, id)
                .findAllAsync().asObservable();
    }
}
