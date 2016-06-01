package com.example.igiagante.thegarden.creation.plants.presentation.views;

import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.FlavorHolder;

import java.util.List;

/**
 * @author Ignacio Giagante, on 1/6/16.
 */
public interface FlavorGalleryView extends IView {

    void loadFlavors(List<FlavorHolder> flavors);
}
