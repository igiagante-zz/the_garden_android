package com.example.igiagante.thegarden.plants.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ActivityComponent;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.plants.getPlants.PlantListFragment;
import com.example.igiagante.thegarden.plants.getPlants.PlantModule;

import dagger.Component;

/**
 * Created by igiagante on 2/5/16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PlantModule.class})
public interface PlantComponent extends ActivityComponent {
    void inject(PlantListFragment plantListFragment);
}