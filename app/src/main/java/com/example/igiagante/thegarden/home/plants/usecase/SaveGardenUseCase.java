package com.example.igiagante.thegarden.home.plants.usecase;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class SaveGardenUseCase extends UseCase<Garden> {

    public SaveGardenUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Garden garden) {
        return null;
    }
}
