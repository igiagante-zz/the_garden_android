package com.example.igiagante.thegarden.home.irrigations.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationDetailView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
@PerActivity
public class IrrigationDetailPresenter extends AbstractPresenter<IrrigationDetailView> {

    private final static String TAG = IrrigationPresenter.class.getSimpleName();

    private final UseCase saveIrrigationUseCase;

    @Inject
    public IrrigationDetailPresenter(@Named("saveIrrigation") UseCase saveIrrigationUseCase) {
        this.saveIrrigationUseCase = saveIrrigationUseCase;
    }

    public void destroy() {
        this.saveIrrigationUseCase.unsubscribe();
        this.view = null;
    }

    public void saveIrrigation(Irrigation irrigation){
        this.saveIrrigationUseCase.execute(irrigation, new SaveIrrigationSubscriber());
    }

    private void notifyIfIrrigationWasPersistedOrUpdated(Irrigation irrigation) {
        getView().notifyIfIrrigationWasPersistedOrUpdated(irrigation);
    }

    private final class SaveIrrigationSubscriber extends DefaultSubscriber<Irrigation> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Irrigation irrigation) {
            IrrigationDetailPresenter.this.notifyIfIrrigationWasPersistedOrUpdated(irrigation);
        }
    }
}
