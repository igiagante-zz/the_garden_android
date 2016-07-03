package com.example.igiagante.thegarden.core.repository.restAPI.service;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
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
public interface DoseRestApi {

    @GET("dose/{id}")
    Observable<Dose> getDose(@Path("id") String id);

    @GET("dose/")
    Observable<List<Dose>> getDoses();

    @POST("dose/")
    Observable<Dose> createDose(@Body RequestBody body);

    @PUT("dose/{id}")
    Observable<Dose> updateDose(@Path("id") String id, @Body RequestBody body);

    @DELETE("dose/{id}")
    Observable<Response<Message>> deleteIrrigation(@Path("id") String id);
}
