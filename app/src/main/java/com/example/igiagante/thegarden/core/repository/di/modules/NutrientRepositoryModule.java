package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.NutrientRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
@Module
public class NutrientRepositoryModule {

    @Provides
    @PerActivity
    NutrientRepositoryManager provideNutrientRepositoryManager(Context context) {
        return new NutrientRepositoryManager(context);
    }
}
