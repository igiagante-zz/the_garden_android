package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.NutrientRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.NutrientTable;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.PlantTable;
import com.example.igiagante.thegarden.core.repository.restAPI.services.NutrientRestApi;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class RestApiNutrientRepository extends BaseRestApiRepository<Nutrient> implements Repository<Nutrient> {

    private final NutrientRestApi api;

    public RestApiNutrientRepository(Context context, Session session) {
        super(context, session);
        api = ServiceFactory.createRetrofitService(NutrientRestApi.class, session.getToken());
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

    @NonNull
    private Observable addOrUpdate(Nutrient nutrient, boolean update) {

        MultipartBody.Builder builder = getMultipartBodyForPostOrPut(nutrient);

        Observable<Nutrient> apiResult;

        if (update) {
            apiResult = api.updateNutrient(nutrient.getId(), builder.build()).asObservable();
        } else {
            apiResult = api.createNutrient(builder.build()).asObservable();
        }

        Nutrient result = execute(apiResult, NutrientRealmRepository.class, update);

        return Observable.just(result);
    }

    @Override
    public Observable<Integer> remove(String nutrientId) {
        return api.deleteNutrient(nutrientId).asObservable()
                .map(response -> response.isSuccessful() ? 1 : -1);
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Nutrient>> query(Specification specification) {
        Observable<List<Nutrient>> nutrients = api.getNutrients();
        return nutrients;
    }

    public Observable<List<Nutrient>> getNutrientsByUser(String username) {
        return api.getNutrientsByUserName(username);
    }

    /**
     * Get {@link okhttp3.MultipartBody.Builder} for method POST or PUT
     *
     * @param nutrient Nutrient to be added in form
     * @return builder
     */
    private MultipartBody.Builder getMultipartBodyForPostOrPut(@NonNull final Nutrient nutrient) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder = addNutrientToRequestBody(builder, nutrient);

        ArrayList<File> files = new ArrayList<>();

        for (Image image : nutrient.getImages()) {
            File file = image.getFile();
            if (file != null) {
                files.add(file);
            }
        }

        return addImagesToRequestBody(builder, files);
    }

    /**
     * Add nutrient to the request body which is a {@link okhttp3.MultipartBody.Builder}
     *
     * @param builder  MultipartBody.Builder
     * @param nutrient Nutrient
     * @return builder
     */
    private MultipartBody.Builder addNutrientToRequestBody(@NonNull final MultipartBody.Builder builder, @NonNull final Nutrient nutrient) {

        if (nutrient.getResourcesIds() != null && !nutrient.getResourcesIds().isEmpty()) {
            String resourcesIds = new Gson().toJson(nutrient.getResourcesIds());
            builder.addFormDataPart(PlantTable.RESOURCES_IDS, resourcesIds);
        }
        return builder.addFormDataPart(NutrientTable.USER_ID, nutrient.getUserId())
                .addFormDataPart(NutrientTable.NAME, nutrient.getName())
                .addFormDataPart(NutrientTable.PH, String.valueOf(nutrient.getPh()))
                .addFormDataPart(NutrientTable.NPK, nutrient.getNpk())
                .addFormDataPart(NutrientTable.DESCRIPTION, nutrient.getName());

    }
}
