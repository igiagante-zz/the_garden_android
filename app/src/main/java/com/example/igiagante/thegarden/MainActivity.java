package com.example.igiagante.thegarden;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.example.igiagante.thegarden.core.AndroidApplication;
import com.example.igiagante.thegarden.core.activity.BaseActivity;
import com.example.igiagante.thegarden.core.network.HttpStatus;
import com.example.igiagante.thegarden.core.network.ServiceFactory;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.service.PlantRestAPI;
import com.example.igiagante.thegarden.repositoryImpl.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.repositoryImpl.realm.specification.PlantSpecification;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
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

    private Subscription getSubscription;

    @Inject
    public HttpStatus httpStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((AndroidApplication) getApplication()).getApplicationComponent().inject(this);

        mBody = (TextView) findViewById(R.id.text_id);

        //PlantRestAPI api = ServiceFactory.createRetrofitService(PlantRestAPI.class);

        // getPlant(api);

        // getPlants(api);

        // updatePlant(api);

        // createPlant(api);

       // deletePlant(api);

        /*
        getSubscription =  NetworkRequest.performAsyncRequest(api.getPlant("57164f1e654be6e328000003"), (plant) -> {
            // Update UI on the main thread
            displayPost(plant);
        });*/

/*
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        Observable.from(numbers).subscribe(System.out::println);*/

        createPlantRealm();

       // mBody.setText("The plant was deleted successfully!");
    }

    private void createPlantRealm() {

        RealmConfiguration config = new RealmConfiguration.Builder(this).
                schemaVersion(1).
                name("test.realm").
                inMemory().
                build();
        Realm.setDefaultConfiguration(config);

        PlantRealmRepository repository = new PlantRealmRepository(config);

        final Realm realm = Realm.getInstance(config);

        realm.beginTransaction();
        Plant plant = new Plant();
        plant.setName("test");
        plant.setSize(30);
        plant.setGardenId("1452345");

        repository.add(plant);

        Plant plantOne = new Plant();
        plant.setName("plantOne");
        plant.setSize(56);
        plant.setGardenId("1452345");

        repository.add(plantOne);

        Plant plantTwo = new Plant();
        plant.setName("plantTwo");
        plant.setSize(23);
        plant.setGardenId("1452345");

        repository.add(plantTwo);

        realm.commitTransaction();

        realm.close();

        repository.query(new PlantSpecification()).subscribe(
                item -> Log.i("test", item.toString())
        );

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
                        if(response.isSuccessful()){
                            mBody.setText("The plant was deleted successfully!");
                        } else {
                            try {

                                mBody.setText(httpStatus.getHttpStatusValue(response.code()) + ' ' +response.errorBody().string());
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

    private void updatePlant(PlantRestAPI api){

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

                        } catch(Throwable ex) {
                            Log.e(TAG, ex.getMessage());
                        }
                    }

                    @Override
                    public final void onNext(Plant response) {
                        mBody.setText(response.toString());
                    }
                });
    }

    private MultipartBody.Builder addResourcesIdsToMultipartBody(MultipartBody.Builder builder, ArrayList<String> resoursesIds){

        for(int i = 0, size = resoursesIds.size(); i<size;i++){
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

    private MultipartBody.Builder addImagesToRequestBody(MultipartBody.Builder builder, ArrayList<File> files){

        for(int i = 0, size = files.size(); i<size;i++){
            String mediaType = "image/" + getMediaType(files.get(i));
            RequestBody image = RequestBody.create(MediaType.parse(mediaType), files.get(i));
            builder.addFormDataPart(files.get(i).getName(), files.get(i).getName(), image);
        }

        return builder;
    }

    private String getMediaType(File file) {
        return file.getAbsolutePath().split("\\.")[1];
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getSubscription != null) {
            getSubscription.unsubscribe();
        }
    }
}
