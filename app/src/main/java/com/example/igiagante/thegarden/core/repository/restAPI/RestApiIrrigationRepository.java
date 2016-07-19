package com.example.igiagante.thegarden.core.repository.restAPI;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.NutrientRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.service.IrrigationRestApi;

import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class RestApiIrrigationRepository  extends BaseRestApiRepository<Irrigation> implements Repository<Irrigation> {

    private final IrrigationRestApi api;

    public RestApiIrrigationRepository(Context context) {
        super(context);
        api = ServiceFactory.createRetrofitService(IrrigationRestApi.class);
    }

    @Override
    public Observable<Irrigation> getById(String id) {
        return api.getIrrigation(id);
    }

    @Override
    public Observable<Irrigation> getByName(String name) {
        return null;
    }

    @Override
    public Observable<Irrigation> add(Irrigation irrigation) {
        return addOrUpdate(irrigation, false);
    }

    @Override
    public Observable<Integer> add(Iterable<Irrigation> items) {
        return null;
    }

    @Override
    public Observable<Irrigation> update(Irrigation irrigation) {
        return addOrUpdate(irrigation, true);
    }

    private Observable addOrUpdate(Irrigation irrigation, boolean update) {

        //MultipartBody.Builder builder = getMultipartBodyForPostOrPut(nutrient);

        Observable<Nutrient> apiResult;

        if (update) {
           // apiResult = api.updateIrrigation(irrigation.getId(), builder.build()).asObservable();
        } else {
           // apiResult = api.createNutrient(builder.build()).asObservable();
        }

        //Nutrient result = execute(apiResult, NutrientRealmRepository.class, update);

        return Observable.just(1);
    }

    @Override
    public Observable<Integer> remove(String id) {
        return null;
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Irrigation>> query(Specification specification) {
        return null;
    }
}
