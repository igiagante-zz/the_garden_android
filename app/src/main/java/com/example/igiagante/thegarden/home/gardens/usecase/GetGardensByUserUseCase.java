package com.example.igiagante.thegarden.home.gardens.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.UserRepositoryManager;
import com.example.igiagante.thegarden.core.repository.realm.specification.UserByNameSpecification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class GetGardensByUserUseCase extends UseCase<String> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final UserRepositoryManager userRepositoryManager;

    @Inject
    public GetGardensByUserUseCase(@NonNull UserRepositoryManager userRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepositoryManager = userRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(String username) {
        UserByNameSpecification userByNameSpecification = new UserByNameSpecification(username);
        return userRepositoryManager.query(userByNameSpecification, username);
    }
}
