package com.example.igiagante.thegarden.home.irrigations.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationView;

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
    private final UseCase updateGardenWithIrrigationUseCase;

    @Inject
    public IrrigationPresenter(@Named("getIrrigations") UseCase getIrrigationsUseCase,
                               @Named("deleteIrrigation") UseCase deleteIrrigationUseCase,
                               @Named("updateGardenWithIrrigation") UseCase updateGardenWithIrrigationUseCase) {
        this.getIrrigationsUseCase = getIrrigationsUseCase;
        this.deleteIrrigationUseCase = deleteIrrigationUseCase;
        this.updateGardenWithIrrigationUseCase = updateGardenWithIrrigationUseCase;
    }

    public void destroy() {
        this.getIrrigationsUseCase.unsubscribe();
        this.deleteIrrigationUseCase.unsubscribe();
        this.updateGardenWithIrrigationUseCase.unsubscribe();
        this.view = null;
    }

    public void deleteIrrigation(String irrigationId) {
        this.deleteIrrigationUseCase.execute(irrigationId, new DeleteIrrigationSubscriber());
    }

    public void updateGarden(Garden garden) {
        this.updateGardenWithIrrigationUseCase.execute(garden, new UpdateGardenSubscriber());
    }

    private void notifyIfIrrigationWasDeleted(Integer result) {
        getView().notifyIfIrrigationWasDeleted();
    }

    private void notifyIfGardenWasUpdated(Garden garden) {
        getView().notifyIfGardenWasUpdated(garden);
    }

    private final class DeleteIrrigationSubscriber extends DefaultSubscriber<Integer> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(Integer result) {
            IrrigationPresenter.this.notifyIfIrrigationWasDeleted(result);
        }
    }

    private final class UpdateGardenSubscriber extends DefaultSubscriber<Garden> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Garden garden) {
            IrrigationPresenter.this.notifyIfGardenWasUpdated(garden);
        }
    }
}
