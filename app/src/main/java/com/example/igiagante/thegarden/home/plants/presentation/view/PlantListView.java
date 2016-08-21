package com.example.igiagante.thegarden.home.plants.presentation.view;

import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 2/5/16.
 */
public interface PlantListView extends IView {

    /**
     * Notify that a plant was deleted from the garden
     */
    void notifyPlantWasDeleted();
}
