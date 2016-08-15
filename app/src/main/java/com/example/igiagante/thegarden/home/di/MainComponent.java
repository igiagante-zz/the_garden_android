package com.example.igiagante.thegarden.home.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.home.gardens.di.GardenModule;
import com.example.igiagante.thegarden.home.irrigations.di.IrrigationModule;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationsFragment;
import com.example.igiagante.thegarden.home.irrigations.presentation.presenters.IrrigationPresenter;
import com.example.igiagante.thegarden.home.plants.di.PlantsModule;
import com.example.igiagante.thegarden.home.plants.presentation.PlantListFragment;
import com.example.igiagante.thegarden.home.gardens.presentation.presenters.GardenPresenter;
import com.example.igiagante.thegarden.home.plants.presentation.presenters.PlantListPresenter;
import com.example.igiagante.thegarden.login.di.LoginModule;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, GardenModule.class, PlantsModule.class,
                IrrigationModule.class, LoginModule.class})
public interface MainComponent {

    // Activities
    void inject(MainActivity mainActivity);

    // Fragments
    void inject(PlantListFragment plantListFragment);
    void inject(IrrigationsFragment irrigationsFragment);

    GardenPresenter gardenPresenter();

    PlantListPresenter plantListPresenter();

    IrrigationPresenter irrigationPresenter();
}
