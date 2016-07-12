package com.example.igiagante.thegarden.core.repository.realm.specification.nutrient;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientByNameSpecification implements RealmSpecification<NutrientRealm> {

    private final String name;

    public NutrientByNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Observable<RealmResults<NutrientRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(NutrientRealm.class)
                .equalTo(Table.NAME, name)
                .findAll().asObservable();
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
