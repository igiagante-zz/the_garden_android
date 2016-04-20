package com.example.igiagante.thegarden.plants.repository.service;

import com.example.igiagante.thegarden.plants.domain.entity.Plant;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by igiagante on 19/4/16.
 */
public interface PlantRestAPI {

    @GET("plant/{id}")
    Observable<Plant> getPlant(@Path("id") String id);

    @GET("plant/")
    Observable<List<Plant>> getPlants();

    @Multipart
    @POST("plant/")
    Observable<Plant> createPlant(@Part("plant") Plant plant, @PartMap() Map<String, RequestBody> mapFileAndName);

    @PUT("plant/{id}")
    Observable<Plant> updatePlant(@Path("id") String id, @Body Plant plant, @PartMap() Map<String, RequestBody> mapFileAndName);
}
