package com.example.igiagante.thegarden.core.repository.restAPI.service;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
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
public interface IrrigationRestApi {

    @GET("irrigation/{id}")
    Observable<Irrigation> getIrrigation(@Path("id") String id);

    @GET("irrigation/")
    Observable<List<Irrigation>> getIrrigations();

    @POST("irrigation/")
    Observable<Irrigation> createIrrigation(@Body RequestBody body);

    @PUT("irrigation/{id}")
    Observable<Irrigation> updateIrrigation(@Path("id") String id, @Body RequestBody body);

    @DELETE("irrigation/{id}")
    Observable<Response<Message>> deleteIrrigation(@Path("id") String id);
}
