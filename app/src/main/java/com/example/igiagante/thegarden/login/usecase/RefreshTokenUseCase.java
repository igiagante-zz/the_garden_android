package com.example.igiagante.thegarden.login.usecase;

import android.content.Context;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.restAPI.authentication.RestUserApi;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 9/8/16.
 */
public class RefreshTokenUseCase extends UseCase<Void> {

    private final RestUserApi api;
    private Session session;

    @Inject
    public RefreshTokenUseCase(Context context, Session session, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.session = session;
        this.api = new RestUserApi(context, session);
    }

    @Override
    protected Observable buildUseCaseObservable(Void v) {
        return api.refreshToken(session.getUser().getId());
    }
}
