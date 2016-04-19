package com.example.igiagante.thegarden.core.network;

import com.example.igiagante.thegarden.plants.domain.entity.Plant;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by igiagante on 19/4/16.
 */
public interface RestAPI {

    @GET("plant/{id}")
    Observable<Plant> getPlant(@Path("id") String id);
}
