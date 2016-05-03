package com.example.igiagante.thegarden.plants.repository.realm;

import android.content.Context;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.PlantTable;
import com.example.igiagante.thegarden.plants.repository.realm.mapper.PlantRealmToPlant;
import com.example.igiagante.thegarden.plants.repository.realm.mapper.PlantToPlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.specification.PlantByIdSpecification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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
    public Observable<Plant> add(final Plant plant) {

        final Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant)));
        realm.close();

        return Observable.just(plant);
    }

    @Override
    public Observable<List<Plant>> add(final Iterable<Plant> plants) {

        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction( realmParam -> {
            for (Plant plant : plants) {
                realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant));

            }
        });

        realm.close();

        return Observable.just(makeCollection(plants));
    }

    private List<Plant>  makeCollection(Iterable<Plant> iter) {
        List<Plant> list = new ArrayList<>();
        for (Plant item : iter) {
            list.add(item);
        }
        return list;
    }

    @Override
    public Observable<Plant> update(Plant plant) {

        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant)));
        realm.close();

        return Observable.just(plant);
    }

    @Override
    public Observable<Plant> remove(Plant plant) {

        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plant.getId()).findFirst();
            plantRealm.deleteFromRealm();
        });

        realm.close();

        return Observable.just(plant);
    }

    @Override
    public Observable<Plant> remove(Specification specification) {

        realm = Realm.getInstance(realmConfiguration);

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final PlantRealm plantRealm = (PlantRealm) realmSpecification.toPlantRealm(realm);

        // copy before the is deleted
        Plant plant = toPlant.map(plantRealm);
        realm.executeTransaction(realmParam -> plantRealm.deleteFromRealm());

        realm.close();

        return Observable.just(plant);
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
