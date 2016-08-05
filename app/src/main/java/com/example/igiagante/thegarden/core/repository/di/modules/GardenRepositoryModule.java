package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.GardenRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 5/7/16.
 */
@Module
public class GardenRepositoryModule {

    @Provides
    @PerActivity
    GardenRepositoryManager provideGardenRepositoryManager(Context context, Session session) {
        return new GardenRepositoryManager(context, session);
    }
}
