package com.example.igiagante.thegarden.core.repository;

import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by igiagante on 26/4/16.
 */
public interface RealmSpecification extends Specification {
    Observable<RealmResults<PlantRealm>> toRealmResults(Realm realm);
}
