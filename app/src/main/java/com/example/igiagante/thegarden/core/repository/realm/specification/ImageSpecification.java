package com.example.igiagante.thegarden.core.repository.realm.specification;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 5/5/16.
 */
public class ImageSpecification implements RealmSpecification {

    @Override
    public Observable<RealmResults<ImageRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(ImageRealm.class).findAll().asObservable();
    }

    @Override
    public RealmResults<ImageRealm> toRealmResults(@NonNull Realm realm) {
        return realm.where(ImageRealm.class).findAll();
    }

    @Override
    public RealmObject toObjectRealm(Realm realm) {
        return null;
    }
}
