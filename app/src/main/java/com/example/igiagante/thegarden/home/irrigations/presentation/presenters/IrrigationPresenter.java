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
    private final UseCase deleteIrrigationUseCase;

    @Inject
    public IrrigationPresenter(@Named("getIrrigations") UseCase getIrrigationsUseCase,
                               @Named("deleteIrrigation") UseCase deleteIrrigationUseCase) {
        this.getIrrigationsUseCase = getIrrigationsUseCase;
        this.deleteIrrigationUseCase = deleteIrrigationUseCase;
    }

    public void destroy() {
        this.getIrrigationsUseCase.unsubscribe();
        this.deleteIrrigationUseCase.unsubscribe();
        this.view = null;
    }

    public void deleteIrrigation(String irrigationId) {
        this.deleteIrrigationUseCase.execute(irrigationId, new DeleteIrrigationSubscriber());
    }


    private void notifyIfIrrigationWasDeleted(Integer result) {
        getView().notifyIfIrrigationWasDeleted();
    }

    private final class DeleteIrrigationSubscriber extends DefaultSubscriber<Integer> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Integer result) {
            IrrigationPresenter.this.notifyIfIrrigationWasDeleted(result);
        }
    }
}
