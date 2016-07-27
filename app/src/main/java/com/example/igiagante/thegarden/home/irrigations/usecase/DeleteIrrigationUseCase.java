package com.example.igiagante.thegarden.home.irrigations.usecase;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.IrrigationRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 27/7/16.
 */
public class DeleteIrrigationUseCase extends UseCase<String> {

    private final IrrigationRepositoryManager irrigationRepositoryManager;

    @Inject
    public DeleteIrrigationUseCase(IrrigationRepositoryManager irrigationRepositoryManager,
                                   ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.irrigationRepositoryManager = irrigationRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(String irrigationId) {
        return this.irrigationRepositoryManager.delete(irrigationId);
    }
}
