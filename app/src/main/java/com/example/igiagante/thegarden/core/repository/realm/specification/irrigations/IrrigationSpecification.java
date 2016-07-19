package com.example.igiagante.thegarden.core.repository.realm.specification.irrigations;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class IrrigationSpecification  implements RealmSpecification<IrrigationRealm> {

    @Override
    public Observable<RealmResults<IrrigationRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(IrrigationRealm.class).findAll().asObservable();
    }

    @Override
    public RealmResults<IrrigationRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(IrrigationRealm.class).findAll();
    }

    @Override
    public IrrigationRealm toObjectRealm(Realm realm) {
        return null;
    }
}
