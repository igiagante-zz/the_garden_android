package com.example.igiagante.thegarden.core.di.components;

import android.app.Activity;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by igiagante on 18/4/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}
