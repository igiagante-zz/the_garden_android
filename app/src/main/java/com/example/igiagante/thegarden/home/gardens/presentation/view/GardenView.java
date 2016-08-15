package com.example.igiagante.thegarden.home.gardens.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;

import java.util.List;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public interface GardenView extends IView {

    /**
     * Load the list of gardens
     */
    void loadGardens(List<GardenHolder> gardens);

    /**
     * Load the data from garden's model
     * @param gardenHolder Garden Object
     */
    void loadGarden(GardenHolder gardenHolder);

    void notifyIfGardenWasPersistedOrUpdated(Garden garden);

    void notifyIfGardenWasDeleted();

    void notifyIfGardenExists(boolean exists);

    void notifyIfUserWasUpdated(User user);
}
