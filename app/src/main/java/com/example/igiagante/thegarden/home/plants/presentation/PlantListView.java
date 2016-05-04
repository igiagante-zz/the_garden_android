package com.example.igiagante.thegarden.home.plants.presentation;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

import java.util.Collection;

/**
 * Created by igiagante on 2/5/16.
 */
public interface PlantListView extends IView {

    /**
     * Render a user list in the UI.
     *
     * @param plants The collection of {@link Plant} that will be shown.
     */
    void renderPlantList(Collection<Plant> plants);
}
