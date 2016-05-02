package com.example.igiagante.thegarden.plants.getPlants;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by igiagante on 2/5/16.
 */
@Module
public class PlantModule {

    public PlantModule() {}

    @Provides
    @PerActivity
    @Named("plantList")
    UseCase provideGetPlantListUseCase(GetPlantList getPlantList) {
        return getPlantList;
    }
}
