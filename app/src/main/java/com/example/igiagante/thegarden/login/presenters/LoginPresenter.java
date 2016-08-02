package com.example.igiagante.thegarden.login.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.login.view.LoginView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
@PerActivity
public class LoginPresenter extends AbstractPresenter<LoginView> {

    private final static String TAG = LoginPresenter.class.getSimpleName();

    private final UseCase registerUserUseCase;

    private final UseCase loginUserUseCase;

    @Inject
    public LoginPresenter(@Named("registerUser") UseCase registerUserUseCase,
                              @Named("loginUser") UseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    public void destroy() {
        this.registerUserUseCase.unsubscribe();
        this.loginUserUseCase.unsubscribe();
        this.view = null;
    }

    public void registerUser(User user) {
        this.registerUserUseCase.execute(user, new RegisterUserSubscriber());
    }

    public void loginUser(User user) {
        this.loginUserUseCase.execute(user, new LoginUserSubscriber());
    }

    private void notifyUserRegistration(String result){
        getView().notifyUserRegistration(result);
    }

    private void notifyUserLogin(String result){
        getView().notifyUserLogin(result);
    }

    private final class RegisterUserSubscriber extends DefaultSubscriber<String> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(String result) {
            LoginPresenter.this.notifyUserRegistration(result);
        }
    }

    private final class LoginUserSubscriber extends DefaultSubscriber<String> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(String result) {
            LoginPresenter.this.notifyUserLogin(result);
        }
    }
}
