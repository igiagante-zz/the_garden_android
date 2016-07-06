package com.example.igiagante.thegarden.home.plants.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.plants.delete_plant.DeletePlantDataUseCase;
import com.example.igiagante.thegarden.home.plants.usecase.DeleteGardenUseCase;
import com.example.igiagante.thegarden.home.plants.usecase.GetGardenUseCase;
import com.example.igiagante.thegarden.home.plants.usecase.GetGardensUseCase;
import com.example.igiagante.thegarden.home.plants.usecase.GetPlantsUseCase;
import com.example.igiagante.thegarden.home.plants.usecase.SaveGardenUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 5/5/16.
 */
@Module
public class PlantModule {

    public PlantModule() {}

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

    @Provides
    @PerActivity
    @Named("gardens")
    UseCase provideGetGardensUseCase(GetGardensUseCase getGardensUseCase) {
        return getGardensUseCase;
    }

    @Provides
    @PerActivity
    @Named("getGarden")
    UseCase provideGetGardenUseCase(GetGardenUseCase getGardenUseCase) {
        return getGardenUseCase;
    }

    @Provides
    @PerActivity
    @Named("saveGarden")
    UseCase provideSaveGardenUseCase(SaveGardenUseCase saveGardenUseCase) {
        return saveGardenUseCase;
    }

    @Provides
    @PerActivity
    @Named("deleteGarden")
    UseCase provideDeleteGardenUseCase(DeleteGardenUseCase deleteGardenUseCase) {
        return deleteGardenUseCase;
    }
}
