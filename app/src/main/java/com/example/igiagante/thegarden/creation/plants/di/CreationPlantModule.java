package com.example.igiagante.thegarden.creation.plants.di;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.usecase.GetImagesUseCase;

import java.util.Collection;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 19/5/16.
 */
@Module
public class CreationPlantModule {

    private Collection<String> imagesFilePath;

    public CreationPlantModule() {}

    public CreationPlantModule(Collection<String> imagesFilePath) {
        this.imagesFilePath = imagesFilePath;
    }

    @Provides
    @PerActivity
    @Named("images")
    UseCase provideGetImagesUseCase(GetImagesUseCase images) {
        return images;
    }

    @Provides @PerActivity @Named("userDetails") UseCase provideGetUserDetailsUseCase(ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread, Context context) {
        return new GetImagesUseCase(threadExecutor, postExecutionThread, context, imagesFilePath);
    }

}
