package com.example.igiagante.thegarden.home.irrigations.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientModule;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationDetailFragment;
import com.example.igiagante.thegarden.home.irrigations.presentation.presenters.IrrigationDetailPresenter;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 21/7/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, IrrigationModule.class, NutrientModule.class})
public interface IrrigationComponent {

    void inject(IrrigationDetailFragment irrigationDetailFragment);

    IrrigationDetailPresenter irrigationDetailPresenter();
}
