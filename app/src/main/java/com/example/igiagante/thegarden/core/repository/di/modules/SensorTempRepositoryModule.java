package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.SensorTempRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
@Module
public class SensorTempRepositoryModule {

    @Provides
    @PerActivity
    SensorTempRepositoryManager provideSensorTempRepositoryManager(Context context) {
        return new SensorTempRepositoryManager(context);
    }
}
