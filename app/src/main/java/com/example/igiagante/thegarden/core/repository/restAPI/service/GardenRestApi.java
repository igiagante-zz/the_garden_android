package com.example.igiagante.thegarden.core.repository.restAPI.service;

import com.example.igiagante.thegarden.core.domain.entity.Garden;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public interface GardenRestApi {

    @GET("garden/{id}")
    Observable<Garden> getGarden(@Path("id") String id);

    @GET("garden/")
    Observable<List<Garden>> getGardens();

    @POST("garden/")
    Observable<Garden> createGarden(@Body RequestBody body);

    @PUT("garden/{id}")
    Observable<Garden> updateGarden(@Path("id") String id, @Body RequestBody body);

}
