package com.example.igiagante.thegarden.core.repository.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.AttributeRepositoryModule;
import com.example.igiagante.thegarden.core.repository.managers.AttributeRepositoryManager;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, AttributeRepositoryModule.class})
public interface AttributeRepositoryComponent {

    AttributeRepositoryManager attributeRepositoryManager();
}
