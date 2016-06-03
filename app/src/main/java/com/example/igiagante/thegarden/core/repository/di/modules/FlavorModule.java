package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.FlavorRepositoryManager;
import com.example.igiagante.thegarden.core.repository.restAPI.RestApiFlavorRepository;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDao;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDaoRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
@Module
public class FlavorModule {

    @Provides
    @PerActivity
    @Named("flavorDao")
    FlavorDao provideFlavorDao(Context context) {
        return new FlavorDao(context);
    }

    @Provides
    @PerActivity
    FlavorDaoRepository provideFlavorDaoRepository(FlavorDao flavorDao) {
        return new FlavorDaoRepository(flavorDao);
    }

    @Provides
    @PerActivity
    RestApiFlavorRepository provideRestApiFlavorRepository() {
        return new RestApiFlavorRepository();
    }

    @Provides
    @PerActivity
    FlavorRepositoryManager provideFlavorRepositoryManager(FlavorDaoRepository flavorDaoRepository,
                                                           RestApiFlavorRepository restApiFlavorRepository) {
        return new FlavorRepositoryManager(flavorDaoRepository, restApiFlavorRepository);
    }
}
