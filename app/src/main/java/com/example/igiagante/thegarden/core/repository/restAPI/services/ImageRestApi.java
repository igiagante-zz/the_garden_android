package com.example.igiagante.thegarden.core.repository.restAPI.services;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author ignaciogiagante, on 9/2/16.
 */

public interface ImageRestApi {

    @GET("image/")
    Observable<List<String>> getImagesPaths();
}
