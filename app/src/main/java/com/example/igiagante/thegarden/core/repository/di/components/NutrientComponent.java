package com.example.igiagante.thegarden.core.repository.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.NutrientModule;
import com.example.igiagante.thegarden.core.repository.managers.NutrientRepositoryManager;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, NutrientModule.class})
public interface NutrientComponent {

    NutrientRepositoryManager nutrientRepositoryManager();
}
