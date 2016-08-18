package com.example.igiagante.thegarden.show_plant;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.creation.plants.di.module.CreatePlantModule;
import com.example.igiagante.thegarden.show_plant.presentation.AttributeDataFragment;
import com.example.igiagante.thegarden.show_plant.presenters.GetAttributesPresenter;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 18/8/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, CreatePlantModule.class})
public interface ShowPlantComponent {

    void inject(AttributeDataFragment attributeDataFragment);

    GetAttributesPresenter getAttributesPresenter();
}
