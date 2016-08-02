package com.example.igiagante.thegarden.core.repository.restAPI.di;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.repository.restAPI.BaseRestApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
@Singleton
@Component(modules = SessionModule.class)
public interface SessionComponent {

    void inject(BaseRestApi baseRestApi);

    Session session();
}
