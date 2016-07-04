package com.example.igiagante.thegarden.home.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.plants.presentation.view.GardenView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
@PerActivity
public class GardenPresenter extends AbstractPresenter<GardenView> {

    private final static String TAG = PlantListPresenter.class.getSimpleName();

    private final UseCase getGardensUseCase;

    @Inject
    public GardenPresenter(@Named("gardens") UseCase getGardensUseCase) {
        this.getGardensUseCase = getGardensUseCase;
    }

    public void destroy() {
        this.getGardensUseCase.unsubscribe();
        this.view = null;
    }

    public void getGardens() {
        getGardensUseCase.execute(null, new GardenSubscriber());
    }

    private void showGardens(List<Garden> gardens) {
        getView().loadGardens(gardens);
    }

    private final class GardenSubscriber extends DefaultSubscriber<List<Garden>> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(List<Garden> gardens) {
            GardenPresenter.this.showGardens(gardens);
        }
    }
}
