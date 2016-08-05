package com.example.igiagante.thegarden.core.repository.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.UserRepositoryModule;
import com.example.igiagante.thegarden.core.repository.managers.UserRepositoryManager;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserRepositoryModule.class})
public interface UserRepositoryComponent {

    UserRepositoryManager userRepositoryManager();
}
