package com.example.igiagante.thegarden.home.charts.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ActivityComponent;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.home.irrigations.di.IrrigationsModule;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationsFragment;
import com.example.igiagante.thegarden.home.plants.di.PlantModule;

import dagger.Component;

/**
 * @author igiagante on 2/5/16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, IrrigationsModule.class})
public interface ChartsComponent extends ActivityComponent {
}
