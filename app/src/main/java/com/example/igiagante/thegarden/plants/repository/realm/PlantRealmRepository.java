package com.example.igiagante.thegarden.plants.repository.realm;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Image;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.PlantTable;
import com.example.igiagante.thegarden.plants.repository.realm.mapper.PlantRealmToPlant;
import com.example.igiagante.thegarden.plants.repository.realm.mapper.PlantToPlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.specification.PlantByIdSpecification;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by igiagante on 26/4/16.
 */
@Singleton
public class PlantRealmRepository implements Repository<Plant> {

    private final Mapper<PlantRealm, Plant> toPlant;
    private final Mapper<Plant, PlantRealm> toPlantRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public PlantRealmRepository(Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name("garden.realm")
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toPlant = new PlantRealmToPlant();
        this.toPlantRealm = new PlantToPlantRealm(realm);
    }

    @Override
    public Observable<Plant> getById(String id) {
        return query(new PlantByIdSpecification(id)).flatMap(Observable::from);
    }

    @Override
    public String add(final Plant plant) {

        final Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant)));
        realm.close();

        return plant.getId();
    }

    @Override
    public int add(final Iterable<Plant> plants) {

        int size = 0;
        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            for (Plant plant : plants) {
                realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant));

            }
        });

        realm.close();

        if (plants instanceof Collection<?>) {
            size = ((Collection<?>) plants).size();
        }

        return size;
    }

    @Override
    public Observable<Plant> update(Plant plant) {

        realm = Realm.getInstance(realmConfiguration);

        PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plant.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            realmParam.copyToRealmOrUpdate(toPlantRealm.copy(plant, plantRealm));
        });
        realm.close();

        return Observable.just(plant);
    }

    @Override
    public int remove(Plant plant) {

        realm = Realm.getInstance(realmConfiguration);

        PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plant.getId()).findFirst();
        realm.executeTransaction(realmParam -> plantRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return plantRealm.isValid() ? 0 : 1;
    }

    @Override
    public int remove(Specification specification) {

        realm = Realm.getInstance(realmConfiguration);

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final PlantRealm plantRealm = (PlantRealm) realmSpecification.toPlantRealm(realm);

        realm.executeTransaction(realmParam -> plantRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return plantRealm.isValid() ? 0 : 1;
    }

    @Override
    public void removeAll() {
        // Delete all
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    public Observable<List<Plant>> query(Specification specification) {

        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<PlantRealm>> realmResults = realmSpecification.toObservableRealmResults(realm);

        // convert Observable<RealmResults<PlantRealm>> into Observable<List<Plant>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(plantRealm -> toPlant.map(plantRealm))
                        .toList());
    }
}
