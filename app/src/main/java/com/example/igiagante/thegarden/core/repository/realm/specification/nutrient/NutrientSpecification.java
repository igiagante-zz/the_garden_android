package com.example.igiagante.thegarden.core.repository.realm.specification.nutrient;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientSpecification implements RealmSpecification<NutrientRealm> {

    @Override
    public Observable<RealmResults<NutrientRealm>> toObservableRealmResults(Realm realm) {
        return null;
    }

    @Override
    public RealmResults<NutrientRealm> toRealmResults(Realm realm) {
        return null;
    }

    @Override
    public NutrientRealm toObjectRealm(Realm realm) {
        return null;
    }
}
