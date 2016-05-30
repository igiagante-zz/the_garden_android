package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;

/**
 * Created by igiagante on 28/4/16.
 */
public class ImageRealmToImage implements Mapper<ImageRealm, Image>{

    @Override
    public Image map(@NonNull ImageRealm imageRealm) {

        Image image = new Image();
        image.setId(imageRealm.getId());
        copy(imageRealm, image);

        return image;
    }

    @Override
    public Image copy(@NonNull ImageRealm imageRealm, @NonNull Image image) {

        image.setName(imageRealm.getName());
        image.setUrl(imageRealm.getUrl());
        image.setThumbnailUrl(imageRealm.getThumbnailUrl());
        image.setType(imageRealm.getType());
        image.setSize(imageRealm.getSize());
        image.setMain(imageRealm.isMain());

        return image;
    }
}
