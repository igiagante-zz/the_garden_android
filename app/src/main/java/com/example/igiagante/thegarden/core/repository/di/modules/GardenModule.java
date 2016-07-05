package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.GardenRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 5/7/16.
 */
@Module
public class GardenModule {

    @Provides
    @PerActivity
    GardenRepositoryManager provideGardenRepositoryManager(Context context) {
        return new GardenRepositoryManager(context);
    }
}
