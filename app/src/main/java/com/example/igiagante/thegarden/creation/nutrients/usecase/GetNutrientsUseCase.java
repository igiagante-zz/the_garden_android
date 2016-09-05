package com.example.igiagante.thegarden.creation.nutrients.usecase;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.NutrientRepositoryManager;
import com.example.igiagante.thegarden.core.repository.realm.specification.nutrient.NutrientsByUserIdSpecification;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class GetNutrientsUseCase extends UseCase<String> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final NutrientRepositoryManager nutrientRepositoryManager;

    @Inject
    public GetNutrientsUseCase(@NonNull NutrientRepositoryManager nutrientRepositoryManager,
                               ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.nutrientRepositoryManager = nutrientRepositoryManager;
    }

    @Override
    protected Observable buildUseCaseObservable(String userId) {
        NutrientsByUserIdSpecification nutrientSpecification = new NutrientsByUserIdSpecification(userId);
        return nutrientRepositoryManager.query(nutrientSpecification);
    }

    @Override
    protected void setRepositoryOrder() {
        repositoryOrder.add(LOCAL_REPOSITORY, REMOTE_REPOSITORY);
    }
}
