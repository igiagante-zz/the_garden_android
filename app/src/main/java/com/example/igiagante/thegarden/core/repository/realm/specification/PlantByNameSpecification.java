package com.example.igiagante.thegarden.core.repository.realm.specification;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantTable;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author giagante on 5/5/16.
 */
public class PlantByNameSpecification implements RealmSpecification<PlantRealm> {

    private final String name;

    public PlantByNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Observable<RealmResults<PlantRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(PlantRealm.class)
                .equalTo(PlantTable.NAME, name)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<PlantRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }

    @Override
    public PlantRealm toObjectRealm(@NonNull Realm realm) {
        return realm.where(PlantRealm.class).equalTo(PlantTable.NAME, name).findFirst();
    }
}
