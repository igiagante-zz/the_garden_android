package com.example.igiagante.thegarden.core.repository.restAPI.service;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plant;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/4/16.
 */
public interface PlantRestAPI {

    @GET("plant/{id}")
    Observable<Plant> getPlant(@Path("id") String id);

    @GET("plant/")
    Observable<List<Plant>> getPlants();

    @FormUrlEncoded
    @POST("plant/")
    Observable<Plant> createPlant(@Part RequestBody body);

    @PUT("plant/{id}")
    Observable<Plant> updatePlant(@Path("id") String id, @Body RequestBody body);

    @DELETE("plant/{id}")
    Observable<Response<Plant>> deletePlant(@Path("id") String id);
}
