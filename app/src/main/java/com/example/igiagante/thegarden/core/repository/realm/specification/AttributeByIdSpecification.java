package com.example.igiagante.thegarden.core.repository.realm.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.AttributeRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeByIdSpecification implements RealmSpecification {

    private final String id;

    public AttributeByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Observable<RealmResults<AttributeRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(AttributeRealm.class)
                .equalTo(PlantTable.Attribute.ID, id)
                .findAll().asObservable();
    }

    @Override
    public RealmResults toRealmResults(Realm realm) {
        return null;
    }

    @Override
    public RealmObject toObjectRealm(Realm realm) {
        return null;
    }
}
