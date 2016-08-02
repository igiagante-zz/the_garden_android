package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.network.Message;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public interface NutrientRestApi {

    @GET("nutrient/{id}")
    Observable<Nutrient> getNutrient(@Path("id") String id);

    @GET("nutrient/")
    Observable<List<Nutrient>> getNutrients();

    @POST("nutrient/")
    Observable<Nutrient> createNutrient(@Body RequestBody body);

    @PUT("nutrient/{id}")
    Observable<Nutrient> updateNutrient(@Path("id") String id, @Body RequestBody body);

    @DELETE("nutrient/{id}")
    Observable<Response<Message>> deleteNutrient(@Path("id") String id);
}
