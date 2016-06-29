package com.example.igiagante.thegarden.creation.plants.presentation.views;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public interface SavePlantView extends IView {

    /**
     * Notify if the plant was persisted successfully or not
     * @param plantId Plant Id
     */
    void notifyIfPlantWasPersisted(String plantId);

    /**
     * Notify if the plant was updated successfully or not
     * @param plant Plant object
     */
    void notifyIfPlantWasUpdated(Plant plant);

}
