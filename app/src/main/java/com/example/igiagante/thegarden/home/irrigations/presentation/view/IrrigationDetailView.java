package com.example.igiagante.thegarden.home.irrigations.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.home.irrigations.presentation.holders.NutrientHolder;

import java.util.List;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public interface IrrigationDetailView extends IView {

    void notifyIfIrrigationWasPersistedOrUpdated(Irrigation irrigation);

    /**
     * Load nutrients which will be used in the irrigations
     *
     * @param nutrients List of nutrients
     */
    void loadNutrients(List<NutrientHolder> nutrients);

    void notifyIfGardenWasUpdated(Garden garden);
}
