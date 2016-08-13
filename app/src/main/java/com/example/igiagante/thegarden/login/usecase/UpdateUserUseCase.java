package com.example.igiagante.thegarden.login.usecase;

import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.UserRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 13/8/16.
 */
public class UpdateUserUseCase extends UseCase<User> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final UserRepositoryManager userRepositoryManager;

    @Inject
    public UpdateUserUseCase(UserRepositoryManager userRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepositoryManager = userRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(User user) {
        return this.userRepositoryManager.updateUser(user);
    }
}
