package com.example.igiagante.thegarden.creation.plants.presentation.views;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 16/8/16.
 */
public interface UpdateGardenView extends IView {

    void notifyIfGardenWasUpdated(Garden garden);
}
