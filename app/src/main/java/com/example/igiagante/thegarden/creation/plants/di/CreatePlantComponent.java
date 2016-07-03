package com.example.igiagante.thegarden.creation.plants.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ActivityComponent;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.core.repository.di.modules.FlavorModule;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.AttributesFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.DescriptionFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.FlavorGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.MainDataFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.PhotoGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.FlavorGalleryPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.MainDataPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.PhotoGalleryPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.SavePlantPresenter;

import dagger.Component;

/**
 * @author igiagante on 6/5/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, CreatePlantModule.class, FlavorModule.class})
public interface CreatePlantComponent extends ActivityComponent {

    void inject(PhotoGalleryFragment photoGalleryFragment);
    void inject(FlavorGalleryFragment flavorGalleryFragment);
    void inject(AttributesFragment attributesFragment);
    void inject(DescriptionFragment descriptionFragment);
    void inject(CreatePlantActivity createPlantActivity);
    void inject(MainDataFragment mainDataFragment);

    PhotoGalleryPresenter photoGalleryPresenter();

    FlavorGalleryPresenter flavorGalleryPresenter();

    SavePlantPresenter savePlantPresenter();

    MainDataPresenter mainDataPresenter();
}
