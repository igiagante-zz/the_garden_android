package com.example.igiagante.thegarden.core.repository.restAPI;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantTable;
import com.example.igiagante.thegarden.core.repository.realm.specification.PlagueByIdSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.service.PlantRestAPI;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/4/16.
 */
@Singleton
public class RestApiPlantRepository implements Repository<Plant> {

    private final static String TAG = RestApiPlantRepository.class.getSimpleName();

    private final PlantRestAPI api;
    private Context mContext;

    @Inject
    public RestApiPlantRepository(Context context) {
        this.mContext = context;
        api = ServiceFactory.createRetrofitService(PlantRestAPI.class);
    }

    @Override
    public Observable<Plant> getById(String id) {
        return api.getPlant(id);
    }

    @Override
    public Observable<String> add(@NonNull final Plant plant) {

        MultipartBody.Builder builder = getMultipartBodyForPostOrPut(plant);
        Observable<Plant> apiResult = api.createPlant(builder.build()).asObservable();

        // get Data From api
        List<Plant> listOne = new ArrayList<>();
        apiResult.subscribe(plant2 -> listOne.add(plant2));

        // persist the plant into database
        PlantRealmRepository dataBase = new PlantRealmRepository(mContext);
        Observable<String> dbResult = dataBase.add(listOne.get(0));

        List<String> list = new ArrayList<>();
        dbResult.subscribe(plantId -> list.add(plantId));

        Observable<String> observable = Observable.just(list.get(0));

        return observable;
    }

    @Override
    public Observable<Integer> add(@NonNull final Iterable<Plant> plants) {

        int size = 0;
        for (Plant plant : plants) {
            add(plant);
        }

        if (plants instanceof Collection<?>) {
            size = ((Collection<?>) plants).size();
        }

        return Observable.just(size);
    }

    @Override
    public Observable<Plant> update(@NonNull final Plant plant) {
        MultipartBody.Builder builder = getMultipartBodyForPostOrPut(plant);
        return api.updatePlant(plant.getId(), builder.build()).asObservable();
    }

    @Override
    public Observable<Integer> remove(@NonNull String plantId) {

        return api.deletePlant(plantId).asObservable()
                .map(response -> response.isSuccessful() ? 0 : -1);
    }

    @Override
    public Observable<Integer> remove(Specification specification) {
        return Observable.just(0);
    }

    @Override
    public void removeAll() {

    }

    @Override
    public Observable<List<Plant>> query(Specification specification) {
        return api.getPlants();
    }

    /**
     * Get {@link okhttp3.MultipartBody.Builder} for methods POST or PUT
     *
     * @param plant Plant to be added in form
     * @return builder
     */
    private MultipartBody.Builder getMultipartBodyForPostOrPut(@NonNull final Plant plant) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder = addPlantToRequestBody(builder, plant);

        ArrayList<File> files = new ArrayList<>();

        for (Image image : plant.getImages()) {
            files.add(image.getFile());
        }

        return addImagesToRequestBody(builder, files);
    }

    /**
     * Add plant to the request body which is a {@link okhttp3.MultipartBody.Builder}
     *
     * @param builder MultipartBody.Builder
     * @param plant   Plant
     * @return builder
     */
    private MultipartBody.Builder addPlantToRequestBody(@NonNull final MultipartBody.Builder builder, @NonNull final Plant plant) {

        if (plant.getResourcesIds() != null && !plant.getResourcesIds().isEmpty()) {
            String resourcesIds = new Gson().toJson(plant.getResourcesIds());
            builder.addFormDataPart(PlantTable.RESOURCES_IDS, resourcesIds);
        }

        if (plant.getFlavors() != null && !plant.getFlavors().isEmpty()) {
            String flavors = new Gson().toJson(plant.getFlavors());
            builder.addFormDataPart(PlantTable.FLAVORS, flavors);
        }

        if (plant.getAttributes() != null && !plant.getAttributes().isEmpty()) {
            String attributes = new Gson().toJson(plant.getAttributes());
            builder.addFormDataPart(PlantTable.ATTRIBUTES, attributes);
        }

        if (plant.getPlagues() != null && !plant.getPlagues().isEmpty()) {
            String plagues = new Gson().toJson(plant.getPlagues());
            builder.addFormDataPart(PlantTable.PLAGUES, plagues);
        }

        return builder.addFormDataPart(PlantTable.NAME, plant.getName())
                .addFormDataPart(PlantTable.SIZE, String.valueOf(plant.getSize()))
                .addFormDataPart(PlantTable.PH_SOIL, String.valueOf(plant.getPhSoil()))
                .addFormDataPart(PlantTable.EC_SOIL, String.valueOf(plant.getEcSoil()))
                .addFormDataPart(PlantTable.FLOWERING_TIME, String.valueOf(plant.getFloweringTime()))
                .addFormDataPart(PlantTable.GENOTYPE, String.valueOf(plant.getGenotype()))
                .addFormDataPart(PlantTable.HARVEST, String.valueOf(plant.getHarvest()))
                .addFormDataPart(PlantTable.DESCRIPTION, String.valueOf(plant.getDescription()))
                .addFormDataPart(PlantTable.GARDEN_ID, plant.getGardenId());
    }

    /**
     * Add images to the request body which is a {@link okhttp3.MultipartBody.Builder}
     *
     * @param builder MultipartBody.Builder
     * @param files   files to be added in builder
     * @return builder
     */
    private MultipartBody.Builder addImagesToRequestBody(MultipartBody.Builder builder, ArrayList<File> files) {

        for (int i = 0, size = files.size(); i < size; i++) {
            String mediaType = "image/" + getMediaType(files.get(i));
            RequestBody image = RequestBody.create(MediaType.parse(mediaType), files.get(i));
            builder.addFormDataPart(files.get(i).getName(), files.get(i).getName(), image);
        }

        return builder;
    }

    /**
     * Get media type from file
     *
     * @param file File to be processed
     * @return type
     */
    private String getMediaType(File file) {

        String type = null;
        String[] chain = null;

        if (file != null) {
            chain = file.getAbsolutePath().split("\\.");
        }

        if (chain != null && chain.length > 0) {
            type = chain[1];
        }
        return type;
    }
}
