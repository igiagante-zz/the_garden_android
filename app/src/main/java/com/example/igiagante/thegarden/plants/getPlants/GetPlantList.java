package com.example.igiagante.thegarden.plants.getPlants;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by igiagante on 2/5/16.
 */
public class GetPlantList extends UseCase {

    private final Repository plantRepository;

    @Inject
    public GetPlantList(Repository plantRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepository = plantRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return plantRepository.query();
    }
}
