package com.example.igiagante.thegarden.plants.views;

import com.example.igiagante.thegarden.core.view.View;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;

import java.util.Collection;

/**
 * Created by igiagante on 2/5/16.
 */
public interface PlantListView extends View{

    /**
     * Render a user list in the UI.
     *
     * @param plants The collection of {@link Plant} that will be shown.
     */
    void renderPlantList(Collection<Plant> plants);
}
