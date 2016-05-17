package com.example.igiagante.thegarden.creation.plants.presentation;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.presenter.PhotoGalleryPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 17/5/16.
 */
@Module
public class CreatePlantModule {

    @Provides
    @PerActivity
    PhotoGalleryPresenter providePhotoGalleryPresenter() {
        return new PhotoGalleryPresenter();
    }
}
