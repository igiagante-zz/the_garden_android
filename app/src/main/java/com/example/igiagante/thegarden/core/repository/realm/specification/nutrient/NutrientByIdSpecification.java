package com.example.igiagante.thegarden.core.repository.realm.specification.nutrient;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class NutrientByIdSpecification  implements RealmSpecification {

    private final String id;

    public NutrientByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Observable<RealmResults<NutrientRealm>> toObservableRealmResults(@NonNull Realm realm) {
        return realm.where(NutrientRealm.class)
                .equalTo(Table.ID, id)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<NutrientRealm> toRealmResults(@NonNull Realm realm) {
        return null;
    }
}
