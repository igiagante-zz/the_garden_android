package com.example.igiagante.thegarden.home.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.GardenRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 5/7/16.
 */
public class DeleteGardenUseCase extends UseCase<String> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final GardenRepositoryManager gardenRepositoryManager;

    @Inject
    public DeleteGardenUseCase(@NonNull GardenRepositoryManager gardenRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gardenRepositoryManager = gardenRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(String gardenId) {
        return gardenRepositoryManager.delete(gardenId);
    }
}
