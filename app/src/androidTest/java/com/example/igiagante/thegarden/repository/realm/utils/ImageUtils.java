package com.example.igiagante.thegarden.repository.realm.utils;

import com.example.igiagante.thegarden.core.domain.entity.Image;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class ImageUtils {

    /**
     * Create one image (domain)
     *
     * @param id   Id
     * @param name Image's name
     * @return image
     */
    public static Image createImage(String id, String name, boolean main) {

        Image image = new Image();
        image.setId(id);
        image.setName(name);
        image.setUrl("url");
        image.setThumbnailUrl("thumbnailUrl");
        image.setType("jpeg");
        image.setSize(4233);
        image.setMain(main);

        return image;
    }
}
