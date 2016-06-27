package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.AttributeRepositoryManager;
import com.example.igiagante.thegarden.core.repository.realm.specification.AttributeSpecification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/6/16.
 */
@PerActivity
public class GetAttributesUseCase extends UseCase<Void> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final AttributeRepositoryManager attributeRepositoryManager;

    @Inject
    public GetAttributesUseCase(@NonNull AttributeRepositoryManager attributeRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.attributeRepositoryManager = attributeRepositoryManager;
        // set repositories order
        this.attributeRepositoryManager.setRepositoriesOrder(getRepositoryOrder());
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        AttributeSpecification specification = new AttributeSpecification();
        return attributeRepositoryManager.query(specification);
    }

    @Override
    protected void setRepositoryOrder() {
        repositoryOrder.add(LOCAL_REPOSITORY, REMOTE_REPOSITORY);
    }
}
