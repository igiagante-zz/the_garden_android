package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.views.UpdateGardenView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 16/8/16.
 */
@PerActivity
public class UpdateGardenPresenter extends AbstractPresenter<UpdateGardenView> {

    private final UseCase saveGardenUseCase;

    @Inject
    public UpdateGardenPresenter(@Named("saveGarden") UseCase saveGardenUseCase) {
        this.saveGardenUseCase = saveGardenUseCase;
    }

    public void destroy() {
        this.saveGardenUseCase.unsubscribe();
        this.view = null;
    }

    public void notifyIfGardenWasUpdated(Garden garden) {
        getView().notifyIfGardenWasUpdated(garden);
    }

    public void updateGarden(Garden garden) {
        saveGardenUseCase.execute(garden, new UpdateGardenSubscriber());
    }

    private final class UpdateGardenSubscriber extends DefaultSubscriber<Garden> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Garden garden) {
            UpdateGardenPresenter.this.notifyIfGardenWasUpdated(garden);
        }
    }
}
