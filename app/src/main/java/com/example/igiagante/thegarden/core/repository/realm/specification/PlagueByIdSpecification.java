package com.example.igiagante.thegarden.core.repository.realm.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class PlagueByIdSpecification implements RealmSpecification {

    private final String id;

    public PlagueByIdSpecification(final String id) {
        this.id = id;
    }

    @Override
    public Observable<RealmResults<PlagueRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(PlagueRealm.class)
                .equalTo(PlantTable.Plague.ID, id)
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
