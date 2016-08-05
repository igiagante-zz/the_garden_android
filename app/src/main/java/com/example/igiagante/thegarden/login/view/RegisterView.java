package com.example.igiagante.thegarden.login.view;

import com.example.igiagante.thegarden.core.presentation.mvp.IView;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public interface RegisterView extends IView{

    void notifyUserRegistration(String result);
}
