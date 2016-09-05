package com.example.igiagante.thegarden.home.plants.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.plants.deletePlant.DeletePlantDataUseCase;
import com.example.igiagante.thegarden.home.plants.usecase.GetPlantsUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 5/5/16.
 */
@Module
public class PlantsModule {

    public PlantsModule() {
    }

    @Provides
    @PerActivity
    @Named("plantList")
    UseCase provideGetPlantListUseCase(GetPlantsUseCase getPlantList) {
        return getPlantList;
    }

    @Provides
    @PerActivity
    @Named("deletePlant")
    UseCase provideDeletePlantDataUseCase(DeletePlantDataUseCase deletePlantDataUseCase) {
        return deletePlantDataUseCase;
    }
}
