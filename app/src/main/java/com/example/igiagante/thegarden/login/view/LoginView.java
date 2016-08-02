package com.example.igiagante.thegarden.login.view;

import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public interface LoginView extends IView {

    void notifyUserRegistration(String result);

    void notifyUserLogin(String result);
}
