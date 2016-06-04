package com.example.igiagante.thegarden.core.repository.di.modules;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.AttributeRepositoryManager;
import com.example.igiagante.thegarden.core.repository.managers.PlagueRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
@Module
public class PlagueModule {

    @Provides
    @PerActivity
    PlagueRepositoryManager providePlagueRepositoryManager() {
        return new PlagueRepositoryManager();
    }
}
