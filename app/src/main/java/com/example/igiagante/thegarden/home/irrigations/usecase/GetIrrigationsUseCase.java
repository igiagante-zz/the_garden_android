package com.example.igiagante.thegarden.home.irrigations.usecase;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.IrrigationRepositoryManager;
import com.example.igiagante.thegarden.core.repository.realm.specification.irrigations.IrrigationSpecification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public class GetIrrigationsUseCase extends UseCase<Void> {

    private final IrrigationRepositoryManager irrigationRepositoryManager;

    @Inject
    public GetIrrigationsUseCase(IrrigationRepositoryManager irrigationRepositoryManager,
                                 ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.irrigationRepositoryManager = irrigationRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        IrrigationSpecification irrigationSpecification = new IrrigationSpecification();
        return irrigationRepositoryManager.query(irrigationSpecification);
    }
}
