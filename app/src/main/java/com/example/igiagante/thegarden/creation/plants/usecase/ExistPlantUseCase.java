package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.PlantRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class ExistPlantUseCase extends UseCase<String> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlantRepositoryManager plantRepositoryManager;

    @Inject
    public ExistPlantUseCase(@NonNull PlantRepositoryManager plantRepositoryManager, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepositoryManager = plantRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(String plantName) {
        Observable<Plant> plantObservable = plantRepositoryManager.getRepositories().get(0).getByName(plantName);

        List<Boolean> list = new ArrayList<>();
        plantObservable.subscribe(plant -> {
            if(plant != null) {
                list.add(Boolean.TRUE);
            } else {
                list.add(Boolean.FALSE);
            }
        });

        return list.isEmpty() ? Observable.just(Boolean.FALSE) : Observable.just(list.get(0));
    }
}
