package com.example.igiagante.thegarden.core.repository.restAPI;

import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.services.ImageRestApi;

import java.util.List;

import rx.Observable;

/**
 * @author ignaciogiagante, on 9/2/16.
 */

public class ImageServiceApi {

    private final ImageRestApi api;

    public ImageServiceApi() {
        this.api = ServiceFactory.createRetrofitService(ImageRestApi.class);
    }

    public Observable<List<String>> getImagesPath() {
        return api.getImagesPaths();
    }
}
