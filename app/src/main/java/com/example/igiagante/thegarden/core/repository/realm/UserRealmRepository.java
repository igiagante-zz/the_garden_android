package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.mapper.UserRealmToUser;
import com.example.igiagante.thegarden.core.repository.realm.mapper.UserToUserRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.UserRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;
import com.example.igiagante.thegarden.core.repository.realm.specification.plant.PlantByNameSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.user.UserByIdSpecification;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserRealmRepository implements Repository<User> {

    private final Mapper<UserRealm, User> toUser;
    private final Mapper<User, UserRealm> toUserRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public UserRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name(Repository.DATABASE_NAME_DEV)
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toUser = new UserRealmToUser(context);
        this.toUserRealm = new UserToUserRealm(realm);
    }

    @Override
    public Observable<User> getById(String id) {
        return query(new UserByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public Observable<User> getByName(String name) {
        return query(new PlantByNameSpecification(name)).flatMap(Observable::from);
    }

    @Override
    public Observable<User> add(User user) {
        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam ->
                realmParam.copyToRealmOrUpdate(toUserRealm.map(user)));

        realm.close();

        return Observable.just(user);
    }

    @Override
    public Observable<Integer> add(Iterable<User> users) {
        return null;
    }

    @Override
    public Observable<User> update(User user) {
        realm = Realm.getInstance(realmConfiguration);

        UserRealm userRealm = realm.where(UserRealm.class).equalTo(Table.ID, user.getId()).findFirst();

        if(userRealm != null) {
            realm.executeTransaction(realmParam -> {
                toUserRealm.copy(user, userRealm);
            });
            realm.close();
        } else {
            Log.d("user", "The user is NULL");
        }

        return Observable.just(user);
    }

    @Override
    public Observable<Integer> remove(String userId) {
        return null;
    }

    @Override
    public void removeAll() {
        // Delete all
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            RealmResults<UserRealm> result = realm.where(UserRealm.class).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    public Observable<List<User>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<UserRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<UserRealm>> into Observable<List<User>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(userRealm -> toUser.map(userRealm))
                        .toList());
    }
}
