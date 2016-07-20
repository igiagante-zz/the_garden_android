package com.example.igiagante.thegarden.core.repository.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.IrrigationModule;
import com.example.igiagante.thegarden.core.repository.managers.IrrigationRepositoryManager;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, IrrigationModule.class})
public interface IrrigationComponent {
    IrrigationRepositoryManager IrrigationRepositoryManager();
}
