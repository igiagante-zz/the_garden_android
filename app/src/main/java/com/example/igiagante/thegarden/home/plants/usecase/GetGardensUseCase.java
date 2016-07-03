package com.example.igiagante.thegarden.home.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.PlantRepositoryManager;
import com.example.igiagante.thegarden.core.repository.realm.specification.PlantSpecification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GetGardensUseCase extends UseCase<Void> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlantRepositoryManager plantRepositoryManager;

    @Inject
    public GetGardensUseCase(@NonNull PlantRepositoryManager plantRepositoryManager, @NonNull ThreadExecutor threadExecutor, @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepositoryManager = plantRepositoryManager;
        // set repositories order
        this.plantRepositoryManager.setRepositoriesOrder(getRepositoryOrder());
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {

        PlantSpecification plantSpecification = new PlantSpecification();

        return plantRepositoryManager.query(plantSpecification);
    }

    @Override
    protected void setRepositoryOrder() {
        repositoryOrder.add(LOCAL_REPOSITORY, REMOTE_REPOSITORY);
    }
}
