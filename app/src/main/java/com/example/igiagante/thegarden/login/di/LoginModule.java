package com.example.igiagante.thegarden.login.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.login.usecase.LoginUserUseCase;
import com.example.igiagante.thegarden.login.usecase.RefreshTokenUseCase;
import com.example.igiagante.thegarden.login.usecase.RegisterUserUseCase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
@Module
public class LoginModule {

    public LoginModule() {
    }

    @Provides
    @PerActivity
    @Named("registerUser")
    UseCase provideRegisterUserUseCase(RegisterUserUseCase registerUserUseCase) {
        return registerUserUseCase;
    }

    @Provides
    @PerActivity
    @Named("loginUser")
    UseCase provideLoginUserUseCase(LoginUserUseCase loginUserUseCase) {
        return loginUserUseCase;
    }

    @Provides
    @PerActivity
    @Named("refreshToken")
    UseCase provideRefreshTokenUseCase(RefreshTokenUseCase refreshTokenUseCase) {
        return refreshTokenUseCase;
    }
}
