package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.views.SavePlantView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
@PerActivity
public class SavePlantPresenter extends AbstractPresenter<SavePlantView> {

    private final static String TAG = SavePlantPresenter.class.getSimpleName();

    private final UseCase savePlantUseCase;

    private final UseCase updateGardenUseCase;

    @Inject
    public SavePlantPresenter(@Named("savePlant") UseCase savePlantUseCase,
                              @Named("updateGarden") UseCase updateGardenUseCase) {
        this.savePlantUseCase = savePlantUseCase;
        this.updateGardenUseCase = updateGardenUseCase;
    }

    public void destroy() {
        this.savePlantUseCase.unsubscribe();
        this.updateGardenUseCase.unsubscribe();
        this.view = null;
    }

    public void savePlant(Plant plant) {
        this.showViewLoading();
        this.savePlantUseCase.execute(plant, new SavePlantSubscriber());
    }

    public void updateGarden(Garden garden){
        this.showViewLoading();
        this.updateGardenUseCase.execute(garden, new UpdateGardenSubscriber());
    }

    private void notifyIfPlantWasPersistedOrUpdated(Plant plant) {
        getView().notifyIfPlantWasPersistedOrUpdated(plant);
    }

    private void notifyIfGardenWasUpdated(Garden garden) {
        getView().notifyIfGardenWasUpdated(garden);
    }

    private void showViewLoading() {
        getView().showLoading();
    }

    private void hideViewLoading() {
        getView().hideLoading();
    }

    private final class SavePlantSubscriber extends DefaultSubscriber<Plant> {

        @Override
        public void onCompleted() {
            SavePlantPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            SavePlantPresenter.this.hideViewLoading();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Plant plant) {
            SavePlantPresenter.this.notifyIfPlantWasPersistedOrUpdated(plant);
        }
    }

    private final class UpdateGardenSubscriber extends DefaultSubscriber<Garden> {

        @Override
        public void onCompleted() {
            SavePlantPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            SavePlantPresenter.this.hideViewLoading();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Garden garden) {
            SavePlantPresenter.this.notifyIfGardenWasUpdated(garden);
        }
    }
}
