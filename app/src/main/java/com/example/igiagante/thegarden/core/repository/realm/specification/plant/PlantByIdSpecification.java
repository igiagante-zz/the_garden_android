package com.example.igiagante.thegarden.core.repository.realm.specification.plant;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author giagante on 5/5/16.
 */
public class PlantByIdSpecification implements RealmSpecification {

    private final String id;

    public PlantByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Observable<RealmResults<PlantRealm>> toObservableRealmResults(@NonNull Realm realm) {

        return realm.where(PlantRealm.class)
                .equalTo(PlantTable.ID, id)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
