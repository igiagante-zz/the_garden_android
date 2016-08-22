package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.mapper.SensorTempRealmToSensorTemp;
import com.example.igiagante.thegarden.core.repository.realm.mapper.SensorTempToSensorTempRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.SensorTempRealm;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class SensorTempRealmRepository implements Repository<SensorTemp> {

    private final Mapper<SensorTempRealm, SensorTemp> toSensorTemp;
    private final Mapper<SensorTemp, SensorTempRealm> toSensorTempRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public SensorTempRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name(Repository.DATABASE_NAME_DEV)
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toSensorTemp = new SensorTempRealmToSensorTemp();
        this.toSensorTempRealm = new SensorTempToSensorTempRealm(realm);
    }

    @Override
    public Observable<SensorTemp> getById(String id) {
        return null;
    }

    @Override
    public Observable<SensorTemp> getByName(String name) {
        return null;
    }

    @Override
    public Observable<SensorTemp> add(SensorTemp sensorTemp) {

        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam ->
                realmParam.copyToRealmOrUpdate(toSensorTempRealm.map(sensorTemp)));
        realm.close();

        return Observable.just(sensorTemp);
    }

    @Override
    public Observable<Integer> add(Iterable<SensorTemp> data) {
        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (SensorTemp sensorTemp : data) {
                realmParam.copyToRealmOrUpdate(toSensorTempRealm.map(sensorTemp));
            }
        });

        realm.close();

        if (data instanceof Collection<?>) {
            size = ((Collection<?>) data).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<SensorTemp> update(SensorTemp item) {
        return null;
    }

    @Override
    public Observable<Integer> remove(String id) {
        return null;
    }

    @Override
    public void removeAll() {
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            RealmResults<SensorTempRealm> result = realm.where(SensorTempRealm.class).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }

    @Override
    public Observable<List<SensorTemp>> query(Specification specification) {

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<SensorTempRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<SensorTempRealm>> into Observable<List<SensorTemp>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(sensorTempRealm -> toSensorTemp.map(sensorTempRealm))
                        .toList());
    }
}
