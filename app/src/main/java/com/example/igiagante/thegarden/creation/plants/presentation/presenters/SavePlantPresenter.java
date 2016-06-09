package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.views.SavePlantView;
import com.example.igiagante.thegarden.home.plants.presentation.PlantListPresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 8/6/16.
 */
@PerActivity
public class SavePlantPresenter extends AbstractPresenter<SavePlantView> {

    private final UseCase savePlantUserCase;

    @Inject
    public SavePlantPresenter(@Named("savePlant") UseCase savePlantUserCase) {
        this.savePlantUserCase = savePlantUserCase;
    }

    public void destroy() {
        this.savePlantUserCase.unsubscribe();
        this.view = null;
    }

    public void notifyIfPlantWasPersist(String plantId) {
        getView().notifyIfPlantWasPersisted(plantId);
    }

    public void savePlant(Plant plant) {
        savePlantUserCase.execute(plant, new SavePlantSubscriber());
    }

    private final class SavePlantSubscriber extends DefaultSubscriber<String> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            Log.e("Error", e.getMessage());
            //PlantListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(String plantId) {
            SavePlantPresenter.this.notifyIfPlantWasPersist(plantId);
        }
    }
}
