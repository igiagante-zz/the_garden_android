package com.example.igiagante.thegarden.creation.plants.usecase;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.managers.PlantRepositoryManager;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
public class SavePlantUseCase extends UseCase<Plant> {

    /**
     * Repository Manager which delegates the actions to the correct repository
     */
    private final PlantRepositoryManager plantRepositoryManager;

    @Inject
    public SavePlantUseCase(@NonNull PlantRepositoryManager plantRepositoryManager,
                            ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.plantRepositoryManager = plantRepositoryManager;
        // set repositories order
        this.plantRepositoryManager.setRepositoriesOrder(getRepositoryOrder());
    }

    @Override
    protected Observable buildUseCaseObservable(Plant plant) {
        //deleteImagesFiles(plant);
        if(TextUtils.isEmpty(plant.getId())) {
            return plantRepositoryManager.add(plant);
        } else {
            return plantRepositoryManager.update(plant);
        }
    }

    @Override
    protected void setRepositoryOrder() {
        repositoryOrder.add(LOCAL_REPOSITORY, REMOTE_REPOSITORY);
    }

    /**
     * Delete images created by RxPaparazzo
     * @param plant Plant object
     */
    private void deleteImagesFiles(Plant plant) {
        for (Image image : plant.getImages()) {
            new File(image.getUrl()).delete();
        }
    }
}
