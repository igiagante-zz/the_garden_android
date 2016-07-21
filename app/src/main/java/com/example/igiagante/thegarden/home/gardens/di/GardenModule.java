package com.example.igiagante.thegarden.home.gardens.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.DeleteGardenUseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.GetGardenUseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.GetGardensUseCase;
import com.example.igiagante.thegarden.home.plants.usecase.SaveGardenUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
@Module
public class GardenModule {

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
