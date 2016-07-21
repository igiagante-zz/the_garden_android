package com.example.igiagante.thegarden.home.irrigations.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

import java.util.List;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public interface IrrigationDetailView extends IView {

    void notifyIfIrrigationWasPersistedOrUpdated(Irrigation irrigation);

    void loadNutrients(List<Nutrient> nutrients);
}
