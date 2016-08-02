package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Plague;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public interface PlagueRestApi {

    @GET("plague/")
    Observable<List<Plague>> getPlagues();
}
