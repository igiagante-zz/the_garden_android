package com.example.igiagante.thegarden.creation.plants.presentation.views;

import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public interface MainDataView extends IView{

    /**
     * Inform if a plant already exits
     */
    void informIfPlantExist(Boolean exist);
}
