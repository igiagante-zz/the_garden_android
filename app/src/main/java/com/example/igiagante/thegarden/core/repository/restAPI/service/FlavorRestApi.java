package com.example.igiagante.thegarden.core.repository.restAPI.service;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 27/5/16.
 */
public interface FlavorRestApi {

    @GET("flavor/")
    Observable<List<Flavor>> getFlavors();
}
