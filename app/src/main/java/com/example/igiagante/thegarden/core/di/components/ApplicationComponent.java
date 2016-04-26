package com.example.igiagante.thegarden.core.di.components;

/**
 * Created by igiagante on 18/4/16.
 */

import android.content.Context;

import com.example.igiagante.thegarden.MainActivity;
import com.example.igiagante.thegarden.core.activity.BaseActivity;
import com.example.igiagante.thegarden.core.di.modules.ApplicationModule;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);
    void inject(MainActivity mainActivity);

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
}
