package com.example.igiagante.thegarden;

import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.igiagante.thegarden.core.network.ServiceFactory;
import com.example.igiagante.thegarden.plants.repository.service.PlantRestAPI;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by igiagante on 28/4/16.
 */
public class RestAPIRepository extends AndroidTestCase {

    private static final String TAG = RestAPIRepository.class.getSimpleName();

    private PlantRestAPI api;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        api = ServiceFactory.createRetrofitService(PlantRestAPI.class);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void getPlant() {

        api.getPlant("571a8e688417b12e6d000009")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(plant -> Log.i(TAG, "NUMBER OF PLANTS " + plant.toString()));
    }

    public void geletePlant() {

        api.deletePlant("571e7293a67112e6160003")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(plant -> Log.i(TAG, "The plant was delete successfully"));
    }

    public void updatePlant(PlantRestAPI api) {

        // resourcesIds in order to check if one or more images should be deleted
        ArrayList<String> resourcesIds = new ArrayList<>();
        resourcesIds.add("571e7293a67112e616000001");
        String json = new Gson().toJson(resourcesIds);

        // Create MultipartBody with resourcesIds, new plant's data and new files
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("resourcesIds", json);
        builder = addPlantToBuilder(builder, "mango_loco");

        // Add new images
        ArrayList<File> files = new ArrayList<>();
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        files.add(new File(folder, "mango3.jpg"));
        files.add(new File(folder, "mango4.jpg"));

        if(!files.isEmpty()) {
            builder = addImagesToRequestBody(builder, files);
        }

        api.updatePlant("571e7293a67112e616000003", builder.build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(plant -> Log.i(TAG, "NUMBER OF PLANTS " + plant.toString()));
    }

    private void createPlant(PlantRestAPI api) {

        MultipartBody.Builder builder = new MultipartBody.Builder();

        builder = addPlantToBuilder(builder, "mango");

        ArrayList<File> files = new ArrayList<>();

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        files.add(new File(folder, "images.jpg"));
        files.add(new File(folder, "mango-lg2.jpg"));

        if(!files.isEmpty()) {
            builder = addImagesToRequestBody(builder, files);
        }

        api.createPlant(builder.build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(plant -> Log.i(TAG, "NUMBER OF PLANTS " + plant.toString()));
    }

    private MultipartBody.Builder addPlantToBuilder(MultipartBody.Builder builder, String name) {

        return builder.setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("size", String.valueOf(30))
                .addFormDataPart("phSoil", String.valueOf(6.0))
                .addFormDataPart("ecSoil", String.valueOf(1.0))
                .addFormDataPart("gardenId", "57164dd6962d5cca28000002")
                .addFormDataPart("mainImage", "mango.jpeg");
    }

    private MultipartBody.Builder addImagesToRequestBody(MultipartBody.Builder builder, ArrayList<File> files) {

        for (int i = 0, size = files.size(); i < size; i++) {
            String mediaType = "image/" + getMediaType(files.get(i));
            RequestBody image = RequestBody.create(MediaType.parse(mediaType), files.get(i));
            builder.addFormDataPart(files.get(i).getName(), files.get(i).getName(), image);
        }

        return builder;
    }

    private String getMediaType(File file) {
        return file.getAbsolutePath().split("\\.")[1];
    }

    private void deleteAll() {

    }

}
