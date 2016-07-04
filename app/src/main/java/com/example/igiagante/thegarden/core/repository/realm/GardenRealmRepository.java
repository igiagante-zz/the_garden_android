package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.mapper.GardenRealmToGarden;
import com.example.igiagante.thegarden.core.repository.realm.mapper.GardenToGardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;
import com.example.igiagante.thegarden.core.repository.realm.specification.GardenByIdSpecification;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealmRepository implements Repository<Garden> {

    private final Mapper<GardenRealm, Garden> toGarden;
    private final Mapper<Garden, GardenRealm> toGardenRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public GardenRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name("garden.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toGarden = new GardenToGardenRealm();
        this.toGardenRealm = new GardenRealmToGarden(realm);
    }

    @Override
    public Observable<Garden> getById(String id) {
        return query(new GardenByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public Observable<Garden> getByName(String name) {
        return null;
    }

    @Override
    public Observable<String> add(Garden garden) {
        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam ->
                realmParam.copyToRealmOrUpdate(toGardenRealm.map(garden)));
        realm.close();

        return Observable.just(garden.getId());
    }

    @Override
    public Observable<Integer> add(Iterable<Garden> gardens) {
        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (Garden garden : gardens) {
                realmParam.copyToRealmOrUpdate(toGardenRealm.map(garden));

            }
        });

        realm.close();

        if (gardens instanceof Collection<?>) {
            size = ((Collection<?>) gardens).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Garden> update(Garden garden) {

        realm = Realm.getInstance(realmConfiguration);

        GardenRealm gardenRealm = realm.where(GardenRealm.class).equalTo(Table.ID, garden.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            toGardenRealm.copy(garden, gardenRealm);
        });

        realm.close();

        return Observable.just(garden);
    }

    @Override
    public Observable<Integer> remove(String gardenId) {
        realm = Realm.getInstance(realmConfiguration);

        GardenRealm gardenRealm = realm.where(GardenRealm.class).equalTo(Table.ID, gardenId).findFirst();

        realm.executeTransaction(realmParam -> gardenRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(gardenRealm.isValid() ? -1 : 1);
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        return null;
    }

    @Override
    public void removeAll() {
        // Delete all
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    public Observable<List<Garden>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<GardenRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<GardenRealm>> into Observable<List<Garden>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(gardenRealm -> toGarden.map(gardenRealm))
                        .toList());
    }
}
