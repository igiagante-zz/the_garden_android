package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.views.MainDataView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
@PerActivity
public class MainDataPresenter extends AbstractPresenter<MainDataView> {

    private final UseCase existPlantUseCase;

    @Inject
    public MainDataPresenter(@Named("existPlant") UseCase existPlantUseCase) {
        this.existPlantUseCase = existPlantUseCase;
    }

    public void existPlant(String plantName) {
        existPlantUseCase.execute(plantName, new MainDataPresenterSubscriber());
    }

    public void destroy() {
        this.existPlantUseCase.unsubscribe();
        this.view = null;
    }

    private void notifyIfPlantExist(Boolean exist) {
        getView().informIfPlantExist(exist);
    }

    private final class MainDataPresenterSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Boolean exist) {
            MainDataPresenter.this.notifyIfPlantExist(exist);
        }
    }
}
