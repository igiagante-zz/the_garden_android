package com.example.igiagante.thegarden.core.repository.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.SensorTempRepositoryModule;
import com.example.igiagante.thegarden.core.repository.managers.SensorTempRepositoryManager;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SensorTempRepositoryModule.class})
public interface SensorTempRepositoryComponent {

    SensorTempRepositoryManager sensorTempRepositoryManager();
}
