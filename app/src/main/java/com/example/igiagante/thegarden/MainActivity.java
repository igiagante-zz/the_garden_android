package com.example.igiagante.thegarden;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.igiagante.thegarden.core.AndroidApplication;
import com.example.igiagante.thegarden.core.activity.BaseActivity;
import com.example.igiagante.thegarden.core.network.HttpStatus;
import com.example.igiagante.thegarden.core.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealm;
import com.example.igiagante.thegarden.plants.repository.service.PlantRestAPI;
import com.example.igiagante.thegarden.repositoryImpl.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.repositoryImpl.realm.PlantRealmToPlant;
import com.example.igiagante.thegarden.repositoryImpl.realm.specification.PlantByNameSpecification;
import com.example.igiagante.thegarden.repositoryImpl.realm.specification.PlantSpecification;
import com.google.gson.Gson;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;

/**
 * Created by igiagante on 18/4/16.
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mBody;

    @Inject
    public HttpStatus httpStatus;

    private Realm realm;
    private RealmConfiguration realmConfig;
    private Mapper<PlantRealm, Plant> toPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((AndroidApplication) getApplication()).getApplicationComponent().inject(this);

        mBody = (TextView) findViewById(R.id.text_id);
        toPlant = new PlantRealmToPlant();

        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(this).build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);

        //basicCRUD(realm);

        //createPlantRealm();

        updatePlantRealm();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void basicCRUD(Realm realm) {

        Log.i("TEST", "Perform basic Create/Read/Update/Delete (CRUD) operations...");

        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.beginTransaction();

        // Add a plant
        PlantRealm plantOne = realm.createObject(PlantRealm.class);
        plantOne.setName("test");
        plantOne.setSize(30);
        plantOne.setGardenId("1452345");

        // When the transaction is committed, all changes a synced to disk.
        realm.commitTransaction();

        // Find the first person (no query conditions) and read a field
        plantOne = realm.where(PlantRealm.class).findFirst();
        Log.i("TEST PERSIST", plantOne.getName() + ":" + plantOne.getSize());

        // Update person in a transaction
        realm.beginTransaction();
        plantOne.setName("Senior Plant");
        Log.i("TEST UPDATE", plantOne.getName());
        realm.commitTransaction();

        // Add a plant
        realm.beginTransaction();
        PlantRealm plantTwo = realm.createObject(PlantRealm.class);
        plantTwo.setName("plantTwo");
        plantTwo.setSize(23);
        plantTwo.setGardenId("1452345");
        realm.commitTransaction();

        // Add a plant
        realm.beginTransaction();
        PlantRealm plantThree = realm.createObject(PlantRealm.class);
        plantThree.setName("plantThree");
        plantThree.setSize(20);
        plantThree.setGardenId("234544");
        realm.commitTransaction();

        String msg = String.valueOf(realm.allObjects(PlantRealm.class).size());

        Log.i("NUMBER OF PLANTS", msg);


        realm.where(PlantRealm.class)
                .findAll().asObservable()
                .flatMap(list ->
                        Observable.from(list)
                                .map(plantRealm -> toPlant.map(plantRealm))
                                .toList())
                .subscribe(
                        item -> Log.i("item", item.toString())
                );

        // Delete all persons
        realm.beginTransaction();
        realm.allObjects(PlantRealm.class).deleteAllFromRealm();
        realm.commitTransaction();

    }

    private void updatePlantRealm() {

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("garden.realm")
                .build();

        PlantRealmRepository repository = new PlantRealmRepository(config);

        Plant plant = new Plant();
        plant.setId("234");
        plant.setName("test");
        plant.setSize(30);
        plant.setGardenId("1452345");

        repository.add(plant);

        PlantByNameSpecification spec = new PlantByNameSpecification("test");

        repository.query(spec).subscribe(plants -> {
                    Plant p = plants.get(0);
                    p.setName("name");
                    repository.update(p);
                }
        );

        spec = new PlantByNameSpecification("name");

        repository.query(spec).subscribe(plants -> {
                    Plant p = plants.get(0);
                    Assert.assertEquals(p.getName(), "name");
                }
        );

        // Delete all persons
        realm.beginTransaction();
        realm.allObjects(PlantRealm.class).deleteAllFromRealm();
        realm.commitTransaction();
    }

    private void createPlantRealm() {

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("garden.realm")
                .build();

        PlantRealmRepository repository = new PlantRealmRepository(config);

        ArrayList<Plant> plants = new ArrayList<>();

        Plant plant = new Plant();
        plant.setId("1");
        plant.setName("test");
        plant.setSize(30);
        plant.setGardenId("1452345");

        plants.add(plant);

        Plant plantOne = new Plant();
        plantOne.setId("2");
        plantOne.setName("plantOne");
        plantOne.setSize(56);
        plantOne.setGardenId("1452345");

        plants.add(plantOne);

        Plant plantTwo = new Plant();
        plantTwo.setId("3");
        plantTwo.setName("plantTwo");
        plantTwo.setSize(23);
        plantTwo.setGardenId("1452345");

        plants.add(plantTwo);

        repository.add(plants);

        String msg = String.valueOf(realm.allObjects(PlantRealm.class).size());

        Log.i("NUMBER OF PLANTS", msg);

        repository.query(new PlantSpecification()).subscribe(
                item -> Log.i("item", item.toString())
        );

        // Delete all persons
        realm.beginTransaction();
        realm.allObjects(PlantRealm.class).deleteAllFromRealm();
        realm.commitTransaction();

    }

    private void deletePlant(PlantRestAPI api) {
        api.deletePlant("571e7293a67112e6160003")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Plant>>() {
                    @Override
                    public final void onCompleted() {
                        Log.e(TAG, "on completed");
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public final void onNext(Response<Plant> response) {
                        if (response.isSuccessful()) {
                            mBody.setText("The plant was deleted successfully!");
                        } else {
                            try {

                                mBody.setText(httpStatus.getHttpStatusValue(response.code()) + ' ' + response.errorBody().string());
                            } catch (IOException e) {

                            }

                        }
                    }
                });
    }

    private void getPlants(PlantRestAPI api) {

        api.getPlants()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Plant>>() {
                    @Override
                    public final void onCompleted() {
                        Log.e(TAG, "on completed");
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public final void onNext(List<Plant> response) {
                        mBody.setText(response.toString());
                    }
                });
    }

    private void getPlant(PlantRestAPI api) {

        api.getPlant("571a8e688417b12e6d000009")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Plant>() {
                    @Override
                    public final void onCompleted() {
                        Log.e(TAG, "on completed");
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public final void onNext(Plant response) {
                        mBody.setText(response.toString());
                    }
                });
    }

    private void updatePlant(PlantRestAPI api) {

        ArrayList<String> resourcesIds = new ArrayList<>();
        resourcesIds.add("571e7293a67112e616000001");
        //resourcesIds.add("571e2d1b5b65ae670c000006");

        String json = new Gson().toJson(resourcesIds);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", "mango_loco")
                .addFormDataPart("size", String.valueOf(35))
                .addFormDataPart("phSoil", String.valueOf(6.2))
                .addFormDataPart("ecSoil", String.valueOf(1.2))
                .addFormDataPart("gardenId", "57164dd6962d5cca28000002")
                .addFormDataPart("mainImage", "mango.jpeg")
                .addFormDataPart("resourcesIds", json);

        // builder = addResourcesIdsToMultipartBody(builder, resourcesIds);

        ArrayList<File> files = new ArrayList<>();

        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                e.printStackTrace();
            }
        });


        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        //files.add(new File(folder, "mango3.jpg"));
        files.add(new File(folder, "mango4.jpg"));

        builder = addImagesToRequestBody(builder, files);

        api.updatePlant("571e7293a67112e616000003", builder.build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Plant>() {
                    @Override
                    public final void onCompleted() {
                        Log.e(TAG, "on completed");
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        try {

                        } catch (Throwable ex) {
                            Log.e(TAG, ex.getMessage());
                        }
                    }

                    @Override
                    public final void onNext(Plant response) {
                        mBody.setText(response.toString());
                    }
                });
    }

    private MultipartBody.Builder addResourcesIdsToMultipartBody(MultipartBody.Builder builder, ArrayList<String> resoursesIds) {

        for (int i = 0, size = resoursesIds.size(); i < size; i++) {
            RequestBody resourceId = RequestBody.create(MediaType.parse("text/plain"), resoursesIds.get(i));
            builder.addFormDataPart(String.valueOf(i), String.valueOf(i), resourceId);
        }

        return builder;
    }


    private void createPlant(PlantRestAPI api) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", "mango")
                .addFormDataPart("size", String.valueOf(30))
                .addFormDataPart("phSoil", String.valueOf(6.0))
                .addFormDataPart("ecSoil", String.valueOf(1.0))
                .addFormDataPart("gardenId", "57164dd6962d5cca28000002");

        ArrayList<File> files = new ArrayList<>();

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        files.add(new File(folder, "images.jpg"));
        files.add(new File(folder, "mango-lg2.jpg"));

        builder = addImagesToRequestBody(builder, files);

        api.createPlant(builder.build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Plant>() {
                    @Override
                    public final void onCompleted() {
                        Log.e(TAG, "on completed");
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public final void onNext(Plant response) {
                        mBody.setText(response.toString());
                    }
                });
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

}
