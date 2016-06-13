package com.example.igiagante.thegarden.show_plant.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.show_plant.usecase.GetPlantDataUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
@Module
public class PlantDataModule {

    @Provides
    @PerActivity
    @Named("plantData")
    UseCase provideGetPlantDataUseCase(GetPlantDataUseCase getPlantDataUseCase) {
        return getPlantDataUseCase;
    }
}
