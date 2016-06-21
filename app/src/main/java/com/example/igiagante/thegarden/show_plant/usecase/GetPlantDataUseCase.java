package com.example.igiagante.thegarden.show_plant.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.PlantRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
@PerActivity
public class GetPlantDataUseCase extends UseCase<String> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlantRepositoryManager plantRepositoryManager;

    @Inject
    public GetPlantDataUseCase(@NonNull PlantRepositoryManager plantRepositoryManager,
                               ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepositoryManager = plantRepositoryManager;
        // set repositories order
        this.plantRepositoryManager.setRepositoriesOrder(getRepositoryOrder());
    }

    @Override
    protected Observable buildUseCaseObservable(String plantId) {
        return null;
    }

    @Override
    protected void setRepositoryOrder() {
        repositoryOrder.add(LOCAL_REPOSITORY, REMOTE_REPOSITORY);
    }
}
