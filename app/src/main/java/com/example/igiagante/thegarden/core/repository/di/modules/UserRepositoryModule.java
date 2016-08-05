package com.example.igiagante.thegarden.core.repository.di.modules;

import android.content.Context;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.repository.managers.UserRepositoryManager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
@Module
public class UserRepositoryModule {

    @Provides
    @PerActivity
    UserRepositoryManager provideUserRepositoryManager(Context context, Session session) {
        return new UserRepositoryManager(context, session);
    }
}
