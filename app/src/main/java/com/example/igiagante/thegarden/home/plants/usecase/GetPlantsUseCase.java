package com.example.igiagante.thegarden.home.plants.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 2/5/16.
 */
public class GetPlantsUseCase extends UseCase<Void> {

    private final Repository plantRepository;

    @Inject
    public GetPlantsUseCase(@NonNull Repository plantRepository, @NonNull ThreadExecutor threadExecutor, @NonNull PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepository = plantRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {
        return plantRepository.query(new Specification() {});
    }
}
