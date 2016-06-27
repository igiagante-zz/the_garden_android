package com.example.igiagante.thegarden.show_plant.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.PlantModule;
import com.example.igiagante.thegarden.show_plant.presentation.GetPlantDataFragment;
import com.example.igiagante.thegarden.show_plant.presentation.GetPlantDataPresenter;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, PlantDataModule.class, PlantModule.class})
public interface PlantDataComponent {

    void inject(GetPlantDataFragment getPlantDataFragment);

    GetPlantDataPresenter getPlantDataPresenter();
}
