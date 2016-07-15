package com.example.igiagante.thegarden.creation.nutrients.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

import java.util.List;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public interface NutrientView extends IView {

    void loadNutrients(List<Nutrient> nutrients);

    void notifyIfNutrientWasDeleted();

    void notifyIfNutrientWasPersistedOrUpdated(Nutrient nutrient);
}
