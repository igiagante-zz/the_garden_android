package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 28/4/16.
 */
public class ImageToImageRealm implements Mapper<Image, ImageRealm> {

    private final Realm realm;

    public ImageToImageRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public ImageRealm map(@NonNull Image image) {

        ImageRealm imageRealm = realm.createObject(ImageRealm.class);
        imageRealm.setId(image.getId());
        copy(image, imageRealm);

        return imageRealm;
    }

    @Override
    public ImageRealm copy(@NonNull Image image, @NonNull ImageRealm imageRealm) {
        imageRealm.setName(image.getName());
        imageRealm.setUrl(image.getUrl());
        imageRealm.setThumbnailUrl(image.getThumbnailUrl());
        imageRealm.setType(image.getType());
        imageRealm.setSize(image.getSize());
        imageRealm.setMain(image.isMain());

        return imageRealm;
    }
}
