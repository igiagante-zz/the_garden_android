package com.example.igiagante.thegarden.core.repository.realm.specification;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.UserRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserByNameSpecification implements RealmSpecification<UserRealm> {

    private final String name;

    public UserByNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Observable<RealmResults<UserRealm>> toObservableRealmResults(Realm realm) {
        return realm.where(UserRealm.class)
                .equalTo(Table.NAME, name)
                .findAll().asObservable();
    }

    @Override
    public RealmResults<UserRealm> toRealmResults(Realm realm) {
        return null;
    }
}
