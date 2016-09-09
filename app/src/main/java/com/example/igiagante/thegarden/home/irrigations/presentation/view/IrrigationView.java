package com.example.igiagante.thegarden.home.irrigations.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public interface IrrigationView extends IView {

    void notifyIfIrrigationWasDeleted();

    void notifyIfGardenWasUpdated(Garden garden);
}
