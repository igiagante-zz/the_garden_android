package com.example.igiagante.thegarden.home.charts.usecase;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiSensorTempRepository;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
@PerActivity
public class SensorTempUseCase extends UseCase<Void> {

    @Inject
    public SensorTempUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        RestApiSensorTempRepository restApiSensorTempRepository = new RestApiSensorTempRepository();
        return restApiSensorTempRepository.query(null);
    }
}
