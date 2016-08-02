package com.example.igiagante.thegarden.core.repository.restAPI.di;

import com.example.igiagante.thegarden.core.Session;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
@Module
public class SessionModule {

    @Provides
    @Singleton
    Session provideSession() {
        return new Session();
    }
}
