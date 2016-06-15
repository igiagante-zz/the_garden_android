package com.example.igiagante.thegarden.core.repository.realm.specification;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 15/6/16.
 */
public class PlagueSpecification implements RealmSpecification<PlagueRealm> {

    @Override
    public Observable<RealmResults<PlagueRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(PlagueRealm.class).findAll().asObservable();
    }

    @Override
    public RealmResults<PlagueRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(PlagueRealm.class).findAll();
    }

    @Override
    public PlagueRealm toObjectRealm(@NonNull Realm realm) {
        return null;
    }
}
