package com.example.igiagante.thegarden.core;

import android.app.Application;

import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.components.DaggerApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ApplicationModule;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        RxPaparazzo.register(this);
        Fresco.initialize(this);
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
