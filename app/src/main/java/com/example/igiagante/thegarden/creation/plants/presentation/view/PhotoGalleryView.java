package com.example.igiagante.thegarden.creation.plants.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

import java.util.Collection;

/**
 * @author Ignacio Giagante, on 1/6/16.
 */
public interface PhotoGalleryView extends IView {

    void addImagesToBuilder(Collection<Image> images);
}
