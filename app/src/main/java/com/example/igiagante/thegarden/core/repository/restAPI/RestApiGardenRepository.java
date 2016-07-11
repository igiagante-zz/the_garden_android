package com.example.igiagante.thegarden.core.repository.restAPI;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;
import com.example.igiagante.thegarden.core.repository.restAPI.service.GardenRestApi;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class RestApiGardenRepository extends BaseRestApiRepository<Garden> implements Repository<Garden> {

    private final GardenRestApi api;

    public RestApiGardenRepository(Context context) {
        super(context);
        api = ServiceFactory.createRetrofitService(GardenRestApi.class);
    }

    @Override
    public Observable<Garden> getById(String id) {
        return api.getGarden(id);
    }

    @Override
    public Observable<Garden> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Garden> add(Garden garden) {
        return addOrUpdate(garden, false);
    }

    @Override
    public Observable<Integer> add(Iterable<Garden> gardens) {
        return null;
    }

    @Override
    public Observable<Garden> update(Garden garden) {
        return addOrUpdate(garden, true);
    }

    private Observable addOrUpdate(Garden garden, boolean update) {

        Observable<Garden> apiResult;

        if(update) {
            apiResult = api.updateGarden(garden.getId(), garden).asObservable();
        } else {
            apiResult = api.createGarden(garden).asObservable();
        }

        Garden result = execute(apiResult, GardenRealmRepository.class, update);

        return Observable.just(result);
    }

    @Override
    public Observable<Integer> remove(String gardenId) {
        return api.deleteGarden(gardenId).asObservable()
                .map(response -> response.isSuccessful() ? 1 : -1);
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Garden>> query(Specification specification) {
        return api.getGardens();
    }

}