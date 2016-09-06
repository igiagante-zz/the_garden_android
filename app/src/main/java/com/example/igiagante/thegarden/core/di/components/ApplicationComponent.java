package com.example.igiagante.thegarden.core.di.components;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.igiagante.thegarden.core.AndroidApplication;
import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.di.modules.ApplicationModule;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.home.plants.usecase.PersistStaticDataUseCase;
import com.example.igiagante.thegarden.widgets.MyWidgetProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    void inject(AndroidApplication androidApplication);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    PersistStaticDataUseCase persistStaticDataUseCase();

    Session session();

    SharedPreferences sharedPreferences();
}
