package com.example.igiagante.thegarden.home.irrigations.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
@PerActivity
public class IrrigationPresenter extends AbstractPresenter<IrrigationView> {

    private final static String TAG = IrrigationPresenter.class.getSimpleName();

    private final UseCase getIrrigationsUseCase;

    @Inject
    public IrrigationPresenter(@Named("getIrrigations") UseCase getIrrigationsUseCase) {
        this.getIrrigationsUseCase = getIrrigationsUseCase;
    }

    public void destroy() {
        this.getIrrigationsUseCase.unsubscribe();
        this.view = null;
    }

    public void getIrrigations(){
        this.getIrrigationsUseCase.execute(null, new IrrigationsSubscriber());
    }

    private void showIrrigations(List<Irrigation> irrigations) {
        getView().loadIrrigations(irrigations);
    }

    private final class IrrigationsSubscriber extends DefaultSubscriber<List<Irrigation>> {

        @Override public void onCompleted() {
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override public void onNext(List<Irrigation> irrigations) {
            IrrigationPresenter.this.showIrrigations(irrigations);
        }
    }
}
