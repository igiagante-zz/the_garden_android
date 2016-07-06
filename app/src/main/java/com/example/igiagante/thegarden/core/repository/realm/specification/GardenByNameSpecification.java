package com.example.igiagante.thegarden.core.repository.realm.specification;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 5/7/16.
 */
public class GardenByNameSpecification implements RealmSpecification<GardenRealm> {

    private final String name;

    public GardenByNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Observable<RealmResults<GardenRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(GardenRealm.class)
                .equalTo(Table.NAME, name)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<GardenRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }

    @Override
    public GardenRealm toObjectRealm(Realm realm) {
        return null;
    }
}
