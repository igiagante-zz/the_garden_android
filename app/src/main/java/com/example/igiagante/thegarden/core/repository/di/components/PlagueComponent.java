package com.example.igiagante.thegarden.core.repository.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.PlagueModule;
import com.example.igiagante.thegarden.core.repository.managers.PlagueRepositoryManager;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PlagueModule.class})
public interface PlagueComponent {

    PlagueRepositoryManager plagueRepositoryManager();
}
