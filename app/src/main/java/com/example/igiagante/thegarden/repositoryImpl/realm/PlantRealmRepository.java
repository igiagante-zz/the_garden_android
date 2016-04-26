package com.example.igiagante.thegarden.repositoryImpl.realm;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.realm.PlantTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by igiagante on 26/4/16.
 */
public class PlantRealmRepository implements Repository<Plant> {

    private final RealmConfiguration realmConfiguration;

    private final Mapper<PlantRealm, Plant> toPlant;


    public PlantRealmRepository(final RealmConfiguration realmConfiguration) {

        this.realmConfiguration = realmConfiguration;

        this.toPlant = new PlantRealmToPlant();
    }

    @Override
    public void add(Plant plant) {

        final Realm realm = Realm.getInstance(realmConfiguration);

        realm.executeTransaction( realm1 -> {

                final PlantRealm plantRealm = realm1.createObject(PlantRealm.class);
                plantRealm.setName(plant.getName());
                plantRealm.setGardenId(plant.getGardenId());
                plantRealm.setEcSoil(plant.getEcSoil());
                plantRealm.setPhSoil(plant.getPhSoil());

        });

        realm.close();
    }

    @Override
    public void add(Iterable<Plant> items) {

    }

    @Override
    public void update(Plant item) {

    }

    @Override
    public void remove(Plant item) {

    }

    @Override
    public void remove(Specification specification) {

    }

    @Override
    public Observable<List<Plant>> query(Specification specification) {

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final Realm realm = Realm.getInstance(realmConfiguration);
        final Observable<RealmResults<PlantRealm>> realmResults = realmSpecification.toRealmResults(realm);

        // convert Observable<RealmResults<PlantRealm>> into Observable<List<PlantRealm>>
        return realmResults.flatMap(list ->
                Observable.from(list)
                        .map(plantRealm -> toPlant.map(plantRealm))
                        .toList());
    }
}
