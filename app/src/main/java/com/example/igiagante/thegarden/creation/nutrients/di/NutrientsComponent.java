package com.example.igiagante.thegarden.creation.nutrients.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ActivityComponent;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.creation.nutrients.presentation.fragments.NutrientListFragment;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientPresenter;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, NutrientsModule.class})
public interface NutrientsComponent extends ActivityComponent {

    void inject(NutrientListFragment nutrientListFragment);

    NutrientPresenter nutrientPresenter();
}
