package com.example.igiagante.thegarden.creation.nutrients.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public interface NutrientDetailView extends IView {

    void notifyIfNutrientWasPersistedOrUpdated(Nutrient nutrient);
}
