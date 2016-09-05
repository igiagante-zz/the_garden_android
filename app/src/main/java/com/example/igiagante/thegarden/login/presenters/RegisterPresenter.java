package com.example.igiagante.thegarden.login.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.login.view.RegisterView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
@PerActivity
public class RegisterPresenter extends AbstractPresenter<RegisterView> {

    private final static String TAG = RegisterPresenter.class.getSimpleName();

    private final UseCase registerUserUseCase;

    @Inject
    public RegisterPresenter(@Named("registerUser") UseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    public void destroy() {
        this.registerUserUseCase.unsubscribe();
        this.view = null;
    }

    public void registerUser(User user) {
        this.registerUserUseCase.execute(user, new RegisterUserSubscriber());
    }

    private void notifyUserRegistration(String result) {
        getView().notifyUserRegistration(result);
    }

    private final class RegisterUserSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(String result) {
            RegisterPresenter.this.notifyUserRegistration(result);
        }
    }
}
