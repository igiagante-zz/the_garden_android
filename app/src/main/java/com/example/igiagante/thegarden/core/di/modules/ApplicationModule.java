package com.example.igiagante.thegarden.core.di.modules;

/**
 * @author Ignacio Giagante, on 18/4/16.
 */

import android.content.Context;

import com.example.igiagante.thegarden.core.AndroidApplication;
import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.UIThread;
import com.example.igiagante.thegarden.core.executor.JobExecutor;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.network.HttpStatus;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.plants.usecase.PersistStaticDataUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides @Singleton
    HttpStatus provideHttpStatus() {
        return new HttpStatus();
    }

    @Provides @Singleton
    UseCase providePersistStaticDataUseCase(PersistStaticDataUseCase persistStaticDataUseCase) {
         return persistStaticDataUseCase;
    }

    @Provides
    @Singleton
    Session provideSession() {
        return new Session();
    }
}
