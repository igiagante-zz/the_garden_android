package com.example.igiagante.thegarden.home.presentation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.presentation.Presenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.core.domain.entity.Plant;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by igiagante on 2/5/16.
 */

@PerActivity
public class PlantListPresenter implements Presenter {

    private final static String TAG = PlantListPresenter.class.getSimpleName();

    private PlantListView plantListView;

    private final UseCase getPlantListUserCase;

    @Inject
    public PlantListPresenter(@Named("plantList") UseCase getPlantListUserCase) {
        this.getPlantListUserCase = getPlantListUserCase;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getPlantListUserCase.unsubscribe();
        this.plantListView = null;
    }

    public void setView(@NonNull PlantListView view) {
        this.plantListView = view;
    }

    public void getPlantList() {
        this.getPlantListUserCase.execute(new PlantListSubscriber());
    }

    private void showPlantsCollectionInView(Collection<Plant> plantsCollection) {
        this.plantListView.renderPlantList(plantsCollection);
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
}
