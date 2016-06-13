package com.example.igiagante.thegarden.show_plant.presentation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
public class GetPlantDataPresenter extends AbstractPresenter<GetPlantDataView> {

    private final static String TAG = GetPlantDataPresenter.class.getSimpleName();

    private GetPlantDataView getPlantDataView;

    private final UseCase getPlantUserCase;

    @Inject
    public GetPlantDataPresenter(@Named("plant") UseCase getPlantUserCase) {
        this.getPlantUserCase = getPlantUserCase;
    }

    public void destroy() {
        this.getPlantUserCase.unsubscribe();
        this.getPlantDataView = null;
    }

    public void setView(@NonNull GetPlantDataView view) {
        this.getPlantDataView = view;
    }

    private void showPlant(Plant plant) {
        getView().loadPlantData(plant);
    }

    /**
     * Load all the data related to the plant
     * @param plantId Plant Id
     */
    public void loadPlantData(String plantId) {
        getPlantUserCase.execute(plantId, new PlantSubscriber());
    }

    private final class PlantSubscriber extends DefaultSubscriber<Plant> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Plant plant) {
            GetPlantDataPresenter.this.showPlant(plant);
        }
    }
}
