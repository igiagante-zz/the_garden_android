package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
public interface AttributeRestApi {

    @GET("attribute/")
    Observable<List<Attribute>> getAttributes();
}
