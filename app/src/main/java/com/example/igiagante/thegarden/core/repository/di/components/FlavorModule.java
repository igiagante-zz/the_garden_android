package com.example.igiagante.thegarden.core.repository.di.components;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.FlavorRepositoryManager;
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
    FlavorDao provideFlavorDao(FlavorDao flavorDao)
    { return flavorDao; }

    @Provides
    @PerActivity
    FlavorDaoRepository provideFlavorDaoRepository(FlavorDaoRepository flavorDaoRepository)
    { return flavorDaoRepository; }

    @Provides
    @PerActivity
    RestApiFlavorRepository provideRestApiFlavorRepository(RestApiFlavorRepository restApiFlavorRepository)
    { return restApiFlavorRepository; }


    @Provides
    @PerActivity
    FlavorRepositoryManager provideFlavorRepositoryManager(FlavorDaoRepository flavorDaoRepository,
                                                           RestApiFlavorRepository restApiFlavorRepository) {
        return new FlavorRepositoryManager(flavorDaoRepository, restApiFlavorRepository);
    }
}
