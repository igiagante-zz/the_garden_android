package com.example.igiagante.thegarden.repositoryImpl.realm.mapper;

import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.plants.domain.entity.Image;
import com.example.igiagante.thegarden.plants.repository.realm.ImageRealm;

/**
 * Created by igiagante on 28/4/16.
 */
public class ImageRealmToImage implements Mapper<ImageRealm, Image>{

    @Override
    public Image map(ImageRealm imageRealm) {

        Image image = new Image();
        image.setId(imageRealm.getId());
        image.setName(imageRealm.getName());
        image.setUrl(imageRealm.getUrl());
        image.setThumbnailUrl(imageRealm.getThumbnailUrl());
        image.setType(imageRealm.getType());
        image.setSize(imageRealm.getSize());
        image.setMain(imageRealm.isMain());

        return image;
    }
}
