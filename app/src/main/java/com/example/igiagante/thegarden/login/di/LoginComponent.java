package com.example.igiagante.thegarden.login.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.login.LoginFragment;
import com.example.igiagante.thegarden.login.presenters.LoginPresenter;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LoginModule.class})
public interface LoginComponent {

    void inject(LoginFragment loginFragment);

    LoginPresenter loginPresenter();
}
