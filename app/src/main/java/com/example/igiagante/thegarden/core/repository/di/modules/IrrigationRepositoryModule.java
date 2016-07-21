package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.IrrigationRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
@Module
public class IrrigationRepositoryModule {

    @Provides
    @PerActivity
    IrrigationRepositoryManager provideIrrigationRepositoryManager(Context context) {
        return new IrrigationRepositoryManager(context);
    }
}
