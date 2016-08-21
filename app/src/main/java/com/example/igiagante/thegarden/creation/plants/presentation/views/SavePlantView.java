package com.example.igiagante.thegarden.creation.plants.presentation.views;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.core.presentation.mvp.LoadDataView;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public interface SavePlantView extends LoadDataView {

    /**
     * Notify if the plant was persisted or updated successfully or not
     * @param plant Plant Object
     */
    void notifyIfPlantWasPersistedOrUpdated(Plant plant);

    void notifyIfGardenWasUpdated(Garden garden);

}
