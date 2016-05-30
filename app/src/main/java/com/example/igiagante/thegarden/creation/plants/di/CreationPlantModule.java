package com.example.igiagante.thegarden.creation.plants.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.usecase.GetFlavorsUseCase;
import com.example.igiagante.thegarden.creation.plants.usecase.GetImagesUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 19/5/16.
 */
@Module
public class CreationPlantModule {

    public CreationPlantModule() {}

    @Provides
    @PerActivity
    @Named("images")
    UseCase provideGetImagesUseCase(GetImagesUseCase images) {
        return images;
    }

    @Provides
    @PerActivity
    @Named("flavors")
    UseCase provideGetFlavorsUseCase(GetFlavorsUseCase flavors) {
        return flavors;
    }
}
