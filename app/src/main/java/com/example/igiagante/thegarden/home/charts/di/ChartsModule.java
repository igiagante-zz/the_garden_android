package com.example.igiagante.thegarden.home.charts.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.charts.usecase.SensorTempUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 21/7/16.
 */
@Module
public class ChartsModule {

    @Provides
    @PerActivity
    UseCase provideSensorTempUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        return new SensorTempUseCase(threadExecutor, postExecutionThread);
    }
}
