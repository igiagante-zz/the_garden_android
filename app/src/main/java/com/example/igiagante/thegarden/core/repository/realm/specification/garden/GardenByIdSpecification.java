package com.example.igiagante.thegarden.core.repository.realm.specification.garden;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class GardenByIdSpecification implements RealmSpecification {

    private final String id;

    public GardenByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Observable<RealmResults<GardenRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(GardenRealm.class)
                .equalTo(Table.ID, id)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<GardenRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
