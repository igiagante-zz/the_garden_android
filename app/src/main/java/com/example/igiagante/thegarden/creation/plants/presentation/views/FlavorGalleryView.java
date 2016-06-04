package com.example.igiagante.thegarden.creation.plants.presentation.views;

import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.FlavorHolder;

import java.util.List;

/**
 * @author Ignacio Giagante, on 1/6/16.
 */
public interface FlavorGalleryView extends IView {

    /**
     * Load flavor holders to be shown in the view
     * @param flavors flavor holder collection
     */
    void loadFlavors(List<FlavorHolder> flavors);
}
