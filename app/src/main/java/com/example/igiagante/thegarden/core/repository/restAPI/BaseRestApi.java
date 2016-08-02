package com.example.igiagante.thegarden.core.repository.restAPI;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.repository.restAPI.di.DaggerSessionComponent;
import com.example.igiagante.thegarden.core.repository.restAPI.di.SessionComponent;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class BaseRestApi {

    private SessionComponent sessionComponent;

    @Inject
    protected Session session;

    public BaseRestApi() {
        initializeInjector();
        sessionComponent.inject(this);
    }

    private void initializeInjector() {
        this.sessionComponent = DaggerSessionComponent.builder()
                .build();
    }
}
