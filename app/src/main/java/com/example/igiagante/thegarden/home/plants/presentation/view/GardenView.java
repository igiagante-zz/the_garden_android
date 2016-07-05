package com.example.igiagante.thegarden.home.plants.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

import java.util.List;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public interface GardenView extends IView {

    /**
     * Load the list of gardens
     */
    void loadGardens(List<Garden> gardens);

    void notifyIfGardenWasPersisted(String gardenId);

    void notifyIfGardenWasUpdated(Garden garden);

    void notifyIfGardenWasDeleted();
}
