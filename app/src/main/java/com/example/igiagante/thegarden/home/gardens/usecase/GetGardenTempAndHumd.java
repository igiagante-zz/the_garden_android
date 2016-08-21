package com.example.igiagante.thegarden.home.gardens.usecase;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiSensorTempRepository;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 21/8/16.
 */
public class GetGardenTempAndHumd extends UseCase<Void> {

    @Inject
    public GetGardenTempAndHumd(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        RestApiSensorTempRepository restApiSensorTempRepository = new RestApiSensorTempRepository();
        return restApiSensorTempRepository.getActualTempAndHumd();
    }
}
