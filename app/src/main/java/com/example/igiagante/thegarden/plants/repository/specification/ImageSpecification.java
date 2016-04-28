package com.example.igiagante.thegarden.plants.repository.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.plants.repository.realm.ImageRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by igiagante on 28/4/16.
 */
public class ImageSpecification implements RealmSpecification {

    @Override
    public Observable<RealmResults<ImageRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(ImageRealm.class).findAll().asObservable();
    }

    @Override
    public RealmResults<ImageRealm> toRealmResults(Realm realm) {
        return realm.where(ImageRealm.class).findAll();
    }

    @Override
    public ImageRealm toPlantRealm(Realm realm) {
        return null;
    }
}
