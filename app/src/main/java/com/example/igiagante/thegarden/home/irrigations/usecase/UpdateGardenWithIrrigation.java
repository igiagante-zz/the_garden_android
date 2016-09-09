package com.example.igiagante.thegarden.home.irrigations.usecase;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * It needs to update the entity garden in Realm Database
 *
 * @author Ignacio Giagante, on 9/9/16.
 */
public class UpdateGardenWithIrrigation extends UseCase<Garden> {

    private Context mContext;

    @Inject
    public UpdateGardenWithIrrigation(Context context, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mContext = context;
    }

    @Override
    protected Observable buildUseCaseObservable(Garden garden) {
        GardenRealmRepository gardenRealmRepository = new GardenRealmRepository(mContext);
        return gardenRealmRepository.update(garden);
    }
}
