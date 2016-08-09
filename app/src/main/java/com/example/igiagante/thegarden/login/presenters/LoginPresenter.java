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

    private final UseCase loginUserUseCase;

    private final UseCase refreshTokenUseCase;

    @Inject
    public LoginPresenter(@Named("loginUser") UseCase loginUserUseCase,
                          @Named("refreshToken") UseCase refreshTokenUseCase) {
        this.loginUserUseCase = loginUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    public void destroy() {
        this.loginUserUseCase.unsubscribe();
        this.refreshTokenUseCase.unsubscribe();
        this.view = null;
    }

    public void loginUser(User user) {
        this.loginUserUseCase.execute(user, new LoginUserSubscriber());
    }

    public void refreshToken(String userId){
        this.refreshTokenUseCase.execute(userId, new RrefreshTokenSubscriber());
    }

    private void notifyUserLogin(String result) {
        getView().notifyUserLogin(result);
    }

    private void sendNewToken(String token){
        getView().sendNewToken(token);
    }

    private final class LoginUserSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(String result) {
            LoginPresenter.this.notifyUserLogin(result);
        }
    }

    private final class RrefreshTokenSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(String token) {
            LoginPresenter.this.sendNewToken(token);
        }
    }
}
