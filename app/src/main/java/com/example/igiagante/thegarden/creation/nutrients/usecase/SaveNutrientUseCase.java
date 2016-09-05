package com.example.igiagante.thegarden.creation.nutrients.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.NutrientRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class SaveNutrientUseCase extends UseCase<Nutrient> {

    private final NutrientRepositoryManager nutrientRepositoryManager;

    @Inject
    public SaveNutrientUseCase(@NonNull NutrientRepositoryManager nutrientRepositoryManager,
                               ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nutrientRepositoryManager = nutrientRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(Nutrient nutrient) {
        if (nutrient.getId() == null) {
            return this.nutrientRepositoryManager.add(nutrient);
        } else {
            return this.nutrientRepositoryManager.update(nutrient);
        }
    }
}
