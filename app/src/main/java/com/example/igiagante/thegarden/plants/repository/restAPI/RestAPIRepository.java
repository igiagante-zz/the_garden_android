package com.example.igiagante.thegarden.plants.repository.restAPI;

import com.example.igiagante.thegarden.core.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Image;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.modelRealm.PlantTable;
import com.example.igiagante.thegarden.plants.repository.restAPI.service.PlantRestAPI;
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
 * Created by igiagante on 19/4/16.
 */
@Singleton
public class RestAPIRepository implements Repository<Plant> {

    private final static String TAG = RestAPIRepository.class.getSimpleName();

    private final PlantRestAPI api;

    @Inject
    public RestAPIRepository() {
        api = ServiceFactory.createRetrofitService(PlantRestAPI.class);
    }

    @Override
    public Observable<Plant> getById(String id) {
        return api.getPlant(id);
    }

    @Override
    public String add(Plant plant) {

        MultipartBody.Builder builder = getMultipartBodyForPostOrPut(plant);
        //return api.createPlant(builder.build()).asObservable();
        return null;
    }

    @Override
    public int add(Iterable<Plant> plants) {

        int size = 0;
        for (Plant plant : plants) {
            add(plant);
        }

        if( plants instanceof Collection<?>) {
            size = ((Collection<?>)plants).size();
        }

        return size;
    }

    @Override
    public Observable<Plant> update(Plant plant) {
        MultipartBody.Builder builder = getMultipartBodyForPostOrPut(plant);
        return api.updatePlant(plant.getId(), builder.build()).asObservable();
    }

    @Override
    public int remove(Plant plant) {
        return 0;
    }

    @Override
    public int remove(Specification specification) {
        return 0;
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
     * @param plant Plant to be added in form
     * @return builder
     */
    private MultipartBody.Builder getMultipartBodyForPostOrPut(Plant plant) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder = addPlantToRequestBody(builder, plant);

        ArrayList<File> files = new ArrayList<>();

        for( Image image : plant.getImages() ) {
            files.add(image.getFile());
        }

        return addImagesToRequestBody(builder, files);
    }

    /**
     * Add plant to the request body which is a {@link okhttp3.MultipartBody.Builder}
     * @param builder MultipartBody.Builder
     * @param plant Plant
     * @return builder
     */
    private MultipartBody.Builder addPlantToRequestBody(MultipartBody.Builder builder, Plant plant) {

        if(!plant.getResourcesIds().isEmpty()) {
            String resourcesIds = new Gson().toJson(plant.getResourcesIds());
            builder.addFormDataPart(PlantTable.RESOURCES_IDS, resourcesIds);
        }

        return builder.addFormDataPart(PlantTable.NAME, plant.getName())
                .addFormDataPart(PlantTable.SIZE, String.valueOf(plant.getSize()))
                .addFormDataPart(PlantTable.PHSOIL, String.valueOf(6.0))
                .addFormDataPart(PlantTable.ECSOIL, String.valueOf(1.0))
                .addFormDataPart(PlantTable.HARVEST, String.valueOf(plant.getHarvest()))
                .addFormDataPart(PlantTable.GARDENID, plant.getGardenId());
    }

    /**
     * Add images to the request body which is a {@link okhttp3.MultipartBody.Builder}
     * @param builder MultipartBody.Builder
     * @param files files to be added in builder
     * @return builder
     */
    private MultipartBody.Builder addImagesToRequestBody(MultipartBody.Builder builder, ArrayList<File> files){

        for(int i = 0, size = files.size(); i<size;i++){
            String mediaType = "image/" + getMediaType(files.get(i));
            RequestBody image = RequestBody.create(MediaType.parse(mediaType), files.get(i));
            builder.addFormDataPart(files.get(i).getName(), files.get(i).getName(), image);
        }

        return builder;
    }

    /**
     * Get media type from file
     * @param file File to be processed
     * @return type
     */
    private String getMediaType(File file) {

        String type = null;
        String [] chain = null;

        if(file != null) {
            chain = file.getAbsolutePath().split("\\.");
        }

        if(chain != null && chain.length > 0) {
            type = chain[1];
        }
        return type;
    }
}
