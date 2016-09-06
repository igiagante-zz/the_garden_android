package com.example.igiagante.thegarden.home.gardens.di;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.DeleteGardenUseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.ExistGardenUseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.GetGardenTempAndHumd;
import com.example.igiagante.thegarden.home.gardens.usecase.GetGardenUseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.GetGardensByUserUseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.GetGardensUseCase;
import com.example.igiagante.thegarden.home.gardens.usecase.SaveGardenUseCase;

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
    @Named("getGardensByUser")
    UseCase provideGetGardensByUserUseCase(GetGardensByUserUseCase getGardensByUserUseCase) {
        return getGardensByUserUseCase;
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

    @Provides
    @PerActivity
    @Named("existGarden")
    UseCase provideExistGardenUseCase(ExistGardenUseCase existGardenUseCase) {
        return existGardenUseCase;
    }

    @Provides
    @PerActivity
    UseCase provideGetGardenTempAndHumd(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new GetGardenTempAndHumd(threadExecutor, postExecutionThread);
    }
}
