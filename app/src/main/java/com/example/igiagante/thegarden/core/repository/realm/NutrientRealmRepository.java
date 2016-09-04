package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.mapper.NutrientRealmToNutrient;
import com.example.igiagante.thegarden.core.repository.realm.mapper.NutrientToNutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;
import com.example.igiagante.thegarden.core.repository.realm.specification.nutrient.NutrientByIdSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.nutrient.NutrientByNameSpecification;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class NutrientRealmRepository implements Repository<Nutrient> {

    private final Mapper<NutrientRealm, Nutrient> toNutrient;
    private final Mapper<Nutrient, NutrientRealm> toNutrientRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public NutrientRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name(Repository.DATABASE_NAME_DEV)
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toNutrient = new NutrientRealmToNutrient();
        this.toNutrientRealm = new NutrientToNutrientRealm(realm);
    }

    @Override
    public Observable<Nutrient> getById(String id) {
        return query(new NutrientByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public Observable<Nutrient> getByName(String name) {
        return query(new NutrientByNameSpecification(name)).flatMap(Observable::from);
    }

    @Override
    public Observable<Nutrient> add(Nutrient nutrient) {
        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam ->
                realmParam.copyToRealmOrUpdate(toNutrientRealm.map(nutrient)));
        realm.close();

        return Observable.just(nutrient);
    }

    @Override
    public Observable<Integer> add(Iterable<Nutrient> nutrients) {
        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (Nutrient nutrient : nutrients) {
                realmParam.copyToRealmOrUpdate(toNutrientRealm.map(nutrient));

            }
        });

        realm.close();

        if (nutrients instanceof Collection<?>) {
            size = ((Collection<?>) nutrients).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Nutrient> update(Nutrient nutrient) {
        realm = Realm.getInstance(realmConfiguration);

        NutrientRealm nutrientRealm = realm.where(NutrientRealm.class).equalTo(Table.ID, nutrient.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            toNutrientRealm.copy(nutrient, nutrientRealm);
        });

        realm.close();

        return Observable.just(nutrient);
    }

    @Override
    public Observable<Integer> remove(String nutrientId) {
        realm = Realm.getInstance(realmConfiguration);

        NutrientRealm nutrientRealm = realm.where(NutrientRealm.class).equalTo(Table.ID, nutrientId).findFirst();

        realm.executeTransaction(realmParam -> nutrientRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(nutrientRealm.isValid() ? -1 : 1);
    }

    @Override
    public void removeAll() {
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            RealmResults<NutrientRealm> result = realm.where(NutrientRealm.class).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }

    @Override
    public Observable<List<Nutrient>> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<NutrientRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<NutrientRealm>> into Observable<List<Nutrient>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(nutrientRealm -> toNutrient.map(nutrientRealm))
                        .toList());
    }
}
