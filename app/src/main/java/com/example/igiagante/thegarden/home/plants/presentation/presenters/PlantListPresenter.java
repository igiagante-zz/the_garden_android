package com.example.igiagante.thegarden.home.plants.presentation.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.home.plants.presentation.view.PlantListView;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 5/5/16.
 */
@PerActivity
public class PlantListPresenter extends AbstractPresenter<PlantListView> {

    private final static String TAG = PlantListPresenter.class.getSimpleName();

    private final UseCase getPlantListUseCase;

    private final UseCase deletePlantUseCase;

    @Inject
    public PlantListPresenter(@Named("plantList") UseCase getPlantListUseCase,
                              @Named("deletePlant") UseCase deletePlantUseCase) {
        this.getPlantListUseCase = getPlantListUseCase;
        this.deletePlantUseCase = deletePlantUseCase;
    }

    public void destroy() {
        this.getPlantListUseCase.unsubscribe();
        this.view = null;
    }

    /**
     * Retrieve all the plant which belong to one garden
     */
    public void getPlantList() {
        this.getPlantListUseCase.execute(null, new PlantListSubscriber());
    }

    /**
     * Delete a plant from one garden
     * @param plantId Plant Id
     */
    public void deletePlant(String plantId) {
        this.deletePlantUseCase.execute(plantId, new DeletePlantSubscriber());
    }

    private void showPlantsCollectionInView(Collection<Plant> plantsCollection) {
        getView().renderPlantList(plantsCollection);
    }

    private void notifyPlantWasDeleted() {
        getView().notifyPlantWasDeleted();
    }

    private final class PlantListSubscriber extends DefaultSubscriber<List<Plant>> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(List<Plant> plants) {
            PlantListPresenter.this.showPlantsCollectionInView(plants);
        }
    }

    private final class DeletePlantSubscriber extends DefaultSubscriber<Integer> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Integer result) {
            PlantListPresenter.this.notifyPlantWasDeleted();
        }
    }
}
