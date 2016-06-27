package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.AttributeRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
@Module
public class AttributeModule {

    @Provides
    @PerActivity
    AttributeRepositoryManager provideAttributeRepositoryManager(Context context) {
        return new AttributeRepositoryManager(context);
    }
}
