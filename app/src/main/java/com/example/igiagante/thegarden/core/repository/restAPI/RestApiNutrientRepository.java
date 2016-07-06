package com.example.igiagante.thegarden.core.repository.restAPI;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.NutrientTable;
import com.example.igiagante.thegarden.core.repository.restAPI.service.NutrientRestApi;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class RestApiNutrientRepository extends BaseRestApiRepository<Nutrient> implements Repository<Nutrient> {

    private final NutrientRestApi api;

    public RestApiNutrientRepository(Context context) {
        super(context);
        api = ServiceFactory.createRetrofitService(NutrientRestApi.class);
    }

    @Override
    public Observable<Nutrient> getById(String id) {
        return api.getNutrient(id);
    }

    @Override
    public Observable<Nutrient> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Nutrient> add(Nutrient nutrient) {
        return addOrUpdate(nutrient, false);
    }

    @Override
    public Observable<Integer> add(Iterable<Nutrient> items) {
        return null;
    }

    @Override
    public Observable<Nutrient> update(Nutrient nutrient) {
        return addOrUpdate(nutrient, true);
    }

    private Observable addOrUpdate(Nutrient nutrient, boolean update) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart(NutrientTable.NAME, nutrient.getName())
                .addFormDataPart(NutrientTable.PH, String.valueOf(nutrient.getPh()))
                .addFormDataPart(NutrientTable.NPK, nutrient.getNpk())
                .addFormDataPart(NutrientTable.DESCRIPTION, nutrient.getName());

        Observable<Nutrient> apiResult;

        if(update) {
            apiResult = api.updateNutrient(nutrient.getId(), builder.build()).asObservable();
        } else {
            apiResult = api.createNutrient(builder.build()).asObservable();
        }

        Nutrient result = execute(apiResult, GardenRealmRepository.class, update);

        return update ? Observable.just(result) : Observable.just(result.getId());
    }

    @Override
    public Observable<Integer> remove(String nutrientId) {
        return api.deleteNutrient(nutrientId).asObservable()
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
    public Observable<List<Nutrient>> query(Specification specification) {
        return api.getNutrients();
    }
}
