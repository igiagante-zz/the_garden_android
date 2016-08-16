package com.example.igiagante.thegarden.creation.plants.usecase;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 16/8/16.
 */
public class UpdateGardenWithPlantsUseCase extends UseCase<Garden> {

    private final GardenRealmRepository gardenRealmRepository;

    @Inject
    public UpdateGardenWithPlantsUseCase(Context context, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gardenRealmRepository = new GardenRealmRepository(context);
    }

    @Override
    protected Observable buildUseCaseObservable(Garden garden) {
       return gardenRealmRepository.update(garden);
    }

    @Override
    protected void setRepositoryOrder() {
        repositoryOrder.add(LOCAL_REPOSITORY, REMOTE_REPOSITORY);
    }
}
