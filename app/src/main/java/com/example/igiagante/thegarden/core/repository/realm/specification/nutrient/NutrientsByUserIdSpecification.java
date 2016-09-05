package com.example.igiagante.thegarden.core.repository.realm.specification.nutrient;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.NutrientTable;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 28/8/16.
 */
public class NutrientsByUserIdSpecification implements RealmSpecification<NutrientRealm> {

    private final String userId;

    public NutrientsByUserIdSpecification(final String userId) {
        this.userId = userId;
    }

    @Override
    public Observable<RealmResults<NutrientRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(NutrientRealm.class)
                .equalTo(NutrientTable.USER_ID, userId)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<NutrientRealm> toRealmResults(Realm realm) {
        return null;
    }
}
