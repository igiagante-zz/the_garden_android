package com.example.igiagante.thegarden.home.charts.presentation;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.charts.usecase.SensorTempUseCase;
import com.example.igiagante.thegarden.home.charts.view.SensorTempView;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
@PerActivity
public class ChartsPresenter extends AbstractPresenter<SensorTempView> {

    private final UseCase sensorTempUseCase;

    @Inject
    public ChartsPresenter(SensorTempUseCase sensorTempUseCase) {
        this.sensorTempUseCase = sensorTempUseCase;
    }

    public void destroy() {
        this.sensorTempUseCase.unsubscribe();
        this.view = null;
    }

    public void getSensorData() {
        this.sensorTempUseCase.execute(null, new SensorTempSubscriber());
    }

    private void showSensorData(List<SensorTemp> data) {
        getView().loadSensorTempData(data);
    }

    private final class SensorTempSubscriber extends DefaultSubscriber<List<SensorTemp>> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(List<SensorTemp> data) {
            ChartsPresenter.this.showSensorData(data);
        }
    }
}
