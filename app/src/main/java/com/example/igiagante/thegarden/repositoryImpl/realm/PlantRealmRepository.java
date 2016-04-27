package com.example.igiagante.thegarden.repositoryImpl.realm;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.PlantTable;

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

    private final RealmConfiguration realmConfiguration;

    public PlantRealmRepository(final RealmConfiguration realmConfiguration) {

        this.realmConfiguration = realmConfiguration;

        this.toPlant = new PlantRealmToPlant();

        final Realm realm = Realm.getInstance(realmConfiguration);

        this.toPlantRealm = new PlantToPlantRealm(realm);
    }

    @Override
    public void add(final Plant plant) {

        final Realm realm = Realm.getInstance(realmConfiguration);

        // Delete all persons
        realm.beginTransaction();
        realm.allObjects(PlantRealm.class).deleteAllFromRealm();
        realm.commitTransaction();

        realm.executeTransaction(realmParam -> realmParam.copyToRealm(toPlantRealm.map(plant)));

        realm.close();
    }

    @Override
    public void add(final Iterable<Plant> plants) {

        final Realm realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction( realmParam -> {
            for (Plant plant : plants) {
                realmParam.copyToRealm(toPlantRealm.map(plant));
            }
        });

        realm.close();
    }

    @Override
    public void update(Plant plant) {

        final Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam -> realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant)));
        realm.close();
    }

    @Override
    public void remove(Plant plant) {

        final Realm realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction(realmParam -> {
            PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plant.getId()).findFirst();
            plantRealm.deleteFromRealm();
        });

        realm.close();
    }

    @Override
    public void remove(Specification specification) {

    }

    @Override
    public Observable<List<Plant>> query(Specification specification) {

        final RealmSpecification realmSpecification = (RealmSpecification) specification;

        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<PlantRealm>> realmResults = realmSpecification.toRealmResults(realm);

        // convert Observable<RealmResults<PlantRealm>> into Observable<List<Plant>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(plantRealm -> toPlant.map(plantRealm))
                        .toList());
    }
}
