package com.example.igiagante.thegarden.core.repository.realm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantTable;
import com.example.igiagante.thegarden.core.repository.realm.specification.PlantByIdSpecification;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.mapper.PlantRealmToPlant;
import com.example.igiagante.thegarden.core.repository.realm.mapper.PlantToPlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.specification.PlantByNameSpecification;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 5/5/16.
 */
public class PlantRealmRepository implements Repository<Plant> {

    private final Mapper<PlantRealm, Plant> toPlant;
    private final Mapper<Plant, PlantRealm> toPlantRealm;

    private Realm realm;
    private final RealmConfiguration realmConfiguration;

    public PlantRealmRepository(@NonNull Context context) {

        this.realmConfiguration = new RealmConfiguration.Builder(context)
                .name("garden.realm")
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);

        this.toPlant = new PlantRealmToPlant(context);
        this.toPlantRealm = new PlantToPlantRealm(realm);
    }

    @Override
    public Observable<Plant> getById(@NonNull String id) {
        return query(new PlantByIdSpecification(id)).flatMap(Observable::from);
    }

    public Observable<Plant> getByName(@NonNull String name) {
        return query(new PlantByNameSpecification(name)).flatMap(Observable::from);
    }

    @Override
    public Observable<String> add(@NonNull final Plant plant) {

        realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(realmParam ->
                realmParam.copyToRealmOrUpdate(toPlantRealm.map(plant)));

        realm.close();

        return Observable.just(plant.getId());
    }

    @Override
    public Observable<Integer> add(@NonNull final Iterable<Plant> plants) {

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

        return Observable.just(size);
    }

    @Override
    public Observable<Plant> update(@NonNull final Plant plant) {

        realm = Realm.getInstance(realmConfiguration);

        PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plant.getId()).findFirst();

        realm.executeTransaction(realmParam -> {
            toPlantRealm.copy(plant, plantRealm);
        });

        realm.close();

        // delete images
        List<ImageRealm> imagesToBeDeleted = getImagesToBeDeleted(plant.getResourcesIds());
        if (!imagesToBeDeleted.isEmpty()) {
            for (ImageRealm imageRealm : imagesToBeDeleted) {
                realm.executeTransaction(realmParam -> imageRealm.deleteFromRealm());
                realm.close();
            }
        }

        return Observable.just(plant);
    }

    /**
     * Filter those images which should be deleted from Realm
     *
     * @param resourcesIds list of resources ids images
     * @return List of ImageRealm Objects
     */
    private List<ImageRealm> getImagesToBeDeleted(List<String> resourcesIds) {

        RealmResults<ImageRealm> all = realm.where(ImageRealm.class).findAll();

        // Filter resources ids
        ArrayList<String> resourcesIdsTemp = new ArrayList<>();

        for (ImageRealm imageRealm : all) {
            resourcesIdsTemp.add(imageRealm.getId());
        }

        if (resourcesIds != null && !resourcesIds.isEmpty()) {
            resourcesIdsTemp.removeAll(resourcesIds);
        }

        // Create array with Images Realm which should be deleted
        List<ImageRealm> imagesToBeDeleted = new ArrayList<>();

        for (ImageRealm imageRealm : all) {
            if (resourcesIdsTemp.contains(imageRealm.getId())) {
                imagesToBeDeleted.add(imageRealm);
            }
        }

        return imagesToBeDeleted;
    }

    @Override
    public Observable<Integer> remove(@NonNull String plantId) {

        realm = Realm.getInstance(realmConfiguration);

        PlantRealm plantRealm = realm.where(PlantRealm.class).equalTo(PlantTable.ID, plantId).findFirst();
        realm.executeTransaction(realmParam -> plantRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, it is because the realm object was deleted
        return Observable.just(plantRealm.isValid() ? -1 : 1);
    }

    @Override
    public Observable<Integer> remove(@NonNull final Specification specification) {

        realm = Realm.getInstance(realmConfiguration);

        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final PlantRealm plantRealm = (PlantRealm) realmSpecification.toObjectRealm(realm);

        realm.executeTransaction(realmParam -> plantRealm.deleteFromRealm());

        realm.close();

        // if plantRealm.isValid() is false, the realm object was deleted
        return Observable.just(plantRealm.isValid() ? 0 : 1);
    }

    @Override
    public void removeAll() {
        // Delete all
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    public Observable<List<Plant>> query(@NonNull final Specification specification) {

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
