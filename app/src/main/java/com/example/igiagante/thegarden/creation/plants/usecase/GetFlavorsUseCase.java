package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.FlavorRepositoryManager;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
@PerActivity
public class GetFlavorsUseCase extends UseCase<Void> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final FlavorRepositoryManager flavorRepositoryManager;

    @Inject
    public GetFlavorsUseCase(@NonNull FlavorRepositoryManager flavorRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.flavorRepositoryManager = flavorRepositoryManager;
        this.flavorRepositoryManager.setRepositoriesOrder(getRepositoryOrder());
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        return flavorRepositoryManager.query(new Specification() {});
    }

    @Override
    protected void setRepositoryOrder() {
        repositoryOrder.add(LOCAL_REPOSITORY, REMOTE_REPOSITORY);
    }
}
