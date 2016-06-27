package com.example.igiagante.thegarden.show_plant.presentation;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
public interface GetPlantDataView extends IView {

    /**
     * Load the data related to the plant
     * @param plant
     */
    void loadPlantData(Plant plant);
}
