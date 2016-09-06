package com.example.igiagante.thegarden.core.repository.realm.specification.irrigations;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseByIdSpecification implements RealmSpecification {

    private final String id;

    public DoseByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Observable<RealmResults<DoseRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(DoseRealm.class)
                .equalTo(Table.ID, id)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<DoseRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
