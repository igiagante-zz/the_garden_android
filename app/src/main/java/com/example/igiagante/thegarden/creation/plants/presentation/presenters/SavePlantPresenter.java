package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
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

    private final UseCase savePlantUseCase;

    @Inject
    public SavePlantPresenter(@Named("savePlant") UseCase savePlantUseCase) {
        this.savePlantUseCase = savePlantUseCase;
    }

    public void destroy() {
        this.savePlantUseCase.unsubscribe();
        this.view = null;
    }

    public void notifyIfPlantWasPersisted(String plantId) {
        getView().notifyIfPlantWasPersisted(plantId);
    }

    public void notifyIfPlantWasUpdated(Plant plant) {
        getView().notifyIfPlantWasUpdated(plant);
    }

    public void savePlant(Plant plant) {
        savePlantUseCase.execute(plant, new SavePlantSubscriber());
    }

    private final class SavePlantSubscriber extends DefaultSubscriber<Object> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            //PlantListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Object object) {
            if(object instanceof String) {
                SavePlantPresenter.this.notifyIfPlantWasPersisted((String)object);
            } else {
                SavePlantPresenter.this.notifyIfPlantWasUpdated((Plant)object);
            }
        }
    }
}
