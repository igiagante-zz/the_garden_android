package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.mapper.IrrigationRealmToIrrigation;
import com.example.igiagante.thegarden.core.repository.realm.mapper.IrrigationToIrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.IrrigationTable;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;
import com.example.igiagante.thegarden.core.repository.realm.specification.irrigations.IrrigationByIdSpecification;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class IrrigationRealmRepository implements Repository<Irrigation> {

    private final Mapper<IrrigationRealm, Irrigation> toIrrigation;
    private final Mapper<Irrigation, IrrigationRealm> toIrrigationRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public IrrigationRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name(Repository.DATABASE_NAME_DEV)
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toIrrigation = new IrrigationRealmToIrrigation();
        this.toIrrigationRealm = new IrrigationToIrrigationRealm(realm);
    }

    @Override
    public Observable<Irrigation> getById(String id) {
        return query(new IrrigationByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public Observable<Irrigation> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Irrigation> add(Irrigation irrigation) {

        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam ->
                realmParam.copyToRealmOrUpdate(toIrrigationRealm.map(irrigation)));
        realm.close();

        return Observable.just(irrigation);
    }

    @Override
    public Observable<Integer> add(Iterable<Irrigation> irrigations) {
        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (Irrigation irrigation : irrigations) {
                realmParam.copyToRealmOrUpdate(toIrrigationRealm.map(irrigation));
            }
        });

        realm.close();

        if (irrigations instanceof Collection<?>) {
            size = ((Collection<?>) irrigations).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Irrigation> update(Irrigation irrigation) {
        realm = Realm.getInstance(realmConfiguration);

        IrrigationRealm irrigationRealm = realm.where(IrrigationRealm.class).equalTo(Table.ID, irrigation.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            toIrrigationRealm.copy(irrigation, irrigationRealm);
        });

        realm.close();

        return Observable.just(irrigation);
    }

    @Override
    public Observable<Integer> remove(String irrigationId) {
        realm = Realm.getInstance(realmConfiguration);

        IrrigationRealm irrigationRealm = realm.where(IrrigationRealm.class).equalTo(Table.ID, irrigationId).findFirst();

        realm.executeTransaction(realmParam -> irrigationRealm.deleteFromRealm());

        realm.close();

        // if irrigationRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(irrigationRealm.isValid() ? -1 : 1);
    }

    @Override
    public void removeAll() {
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            RealmResults<IrrigationRealm> result = realm.where(IrrigationRealm.class).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }

    public void removeIrrigationsByGardenId(String gardenId) {
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            RealmResults<IrrigationRealm> result = realm.where(IrrigationRealm.class)
                    .equalTo(IrrigationTable.GARDEN_ID, gardenId).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }

    @Override
    public Observable<List<Irrigation>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<IrrigationRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<IrrigationRealm>> into Observable<List<Irrigation>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(irrigationRealm -> toIrrigation.map(irrigationRealm))
                        .toList());
    }
}
