package com.example.igiagante.thegarden.login.usecase;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.restAPI.authentication.RestUserApi;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class LoginUserUseCase  extends UseCase<User> {

    private final RestUserApi api;

    @Inject
    public LoginUserUseCase(Session session, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.api = new RestUserApi(session);
    }

    @Override
    protected Observable buildUseCaseObservable(User user) {
        return api.loginUser(user.getUserName(), user.getPassword());
    }
}
