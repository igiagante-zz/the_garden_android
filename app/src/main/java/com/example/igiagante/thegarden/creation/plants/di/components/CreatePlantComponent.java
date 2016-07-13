package com.example.igiagante.thegarden.creation.plants.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.FlavorModule;
import com.example.igiagante.thegarden.creation.plants.di.module.CreatePlantModule;
import com.example.igiagante.thegarden.creation.plants.di.module.GalleryModule;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.AttributesFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.DescriptionFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.FlavorGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.MainDataFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.FlavorGalleryPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.MainDataPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.SavePlantPresenter;

import dagger.Component;

/**
 * @author igiagante on 6/5/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, CreatePlantModule.class, FlavorModule.class, GalleryModule.class})
public interface CreatePlantComponent extends GalleryComponent {

    // Fragments
    void inject(FlavorGalleryFragment flavorGalleryFragment);
    void inject(AttributesFragment attributesFragment);
    void inject(DescriptionFragment descriptionFragment);
    void inject(MainDataFragment mainDataFragment);

    // Activities
    void inject(CreatePlantActivity createPlantActivity);

    FlavorGalleryPresenter flavorGalleryPresenter();

    SavePlantPresenter savePlantPresenter();

    MainDataPresenter mainDataPresenter();
}
