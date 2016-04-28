package com.example.igiagante.thegarden.repositoryImpl.realm;

import android.content.Context;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.PlantTable;
import com.example.igiagante.thegarden.repositoryImpl.realm.mapper.PlantRealmToPlant;
import com.example.igiagante.thegarden.repositoryImpl.realm.mapper.PlantToPlantRealm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by igiagante on 26/4/16.
 */
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
    public void add(final Plant plant) {

        final Realm realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> realmParam.copyToRealm(toPlantRealm.map(plant)));

        realm.close();
    }

    @Override
    public void add(final Iterable<Plant> plants) {

        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction( realmParam -> {
            for (Plant plant : plants) {
                realmParam.copyToRealm(toPlantRealm.map(plant));
            }
        });

        realm.close();
    }

    @Override
    public void update(Plant plant) {

        realm = Realm.getInstance(realmConfiguration);

        final Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant)));
        realm.close();
    }

    @Override
    public void remove(Plant plant) {

        realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plant.getId()).findFirst();
            plantRealm.deleteFromRealm();
        });

        realm.close();
    }

    @Override
    public void remove(Specification specification) {

        realm = Realm.getInstance(realmConfiguration);
        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        realm.executeTransaction(realmParam -> {
            PlantRealm plantRealm = (PlantRealm) realmSpecification.toPlantRealm(realm);
            plantRealm.deleteFromRealm();
        });

        realm.close();
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
