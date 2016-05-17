package com.example.igiagante.thegarden.creation.plants.usecase;

import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 17/5/16.
 */
public class ImagePickerUseCase extends UseCase {

    public ImagePickerUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }
}
