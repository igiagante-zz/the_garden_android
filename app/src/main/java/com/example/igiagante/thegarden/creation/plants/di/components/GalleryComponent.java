package com.example.igiagante.thegarden.creation.plants.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ActivityComponent;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.creation.plants.di.module.GalleryModule;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.PhotoGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.PhotoGalleryPresenter;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 13/7/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GalleryModule.class})
public interface GalleryComponent extends ActivityComponent {

    void inject(PhotoGalleryFragment photoGalleryFragment);

    PhotoGalleryPresenter photoGalleryPresenter();
}
