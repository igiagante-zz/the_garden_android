package com.example.igiagante.thegarden.home.charts.usecase;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.SensorTempRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
public class SensorTempUseCase extends UseCase<Void> {

    private final SensorTempRepositoryManager sensorTempRepositoryManager;

    @Inject
    public SensorTempUseCase(SensorTempRepositoryManager sensorTempRepositoryManager,
                             ThreadExecutor threadExecutor,
                             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.sensorTempRepositoryManager = sensorTempRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        return sensorTempRepositoryManager.getSensorData();
    }
}
