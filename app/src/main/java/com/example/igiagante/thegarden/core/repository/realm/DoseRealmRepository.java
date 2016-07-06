package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.mapper.DoseRealmToDose;
import com.example.igiagante.thegarden.core.repository.realm.mapper.DoseToDoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;
import com.example.igiagante.thegarden.core.repository.realm.specification.DoseByIdSpecification;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseRealmRepository implements Repository<Dose> {

    private final Mapper<DoseRealm, Dose> toDose;
    private final Mapper<Dose, DoseRealm> toDoseRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public DoseRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name("garden.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toDose = new DoseRealmToDose();
        this.toDoseRealm = new DoseToDoseRealm(realm);
    }

    @Override
    public Observable<Dose> getById(String id) {
        return query(new DoseByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public Observable<Dose> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Dose> add(Dose dose) {
        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam ->
                realmParam.copyToRealmOrUpdate(toDoseRealm.map(dose)));
        realm.close();

        return Observable.just(dose);
    }

    @Override
    public Observable<Integer> add(Iterable<Dose> doses) {
        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (Dose dose : doses) {
                realmParam.copyToRealmOrUpdate(toDoseRealm.map(dose));

            }
        });

        realm.close();

        if (doses instanceof Collection<?>) {
            size = ((Collection<?>) doses).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Dose> update(Dose dose) {
        realm = Realm.getInstance(realmConfiguration);

        DoseRealm doseRealm = realm.where(DoseRealm.class).equalTo(Table.ID, dose.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            toDoseRealm.copy(dose, doseRealm);
        });

        realm.close();

        return Observable.just(dose);
    }

    @Override
    public Observable<Integer> remove(String doseId) {
        realm = Realm.getInstance(realmConfiguration);

        DoseRealm doseRealm = realm.where(DoseRealm.class).equalTo(Table.ID, doseId).findFirst();

        realm.executeTransaction(realmParam -> doseRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(doseRealm.isValid() ? -1 : 1);
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        return null;
    }

    @Override
    public void removeAll() {
        //Delete all
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    public Observable<List<Dose>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<DoseRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<DoseRealm>> into Observable<List<Dose>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(doseRealm -> toDose.map(doseRealm))
                        .toList());
    }
}
