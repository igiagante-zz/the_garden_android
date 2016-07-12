package com.example.igiagante.thegarden.creation.nutrients.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientDetailView;
import com.example.igiagante.thegarden.home.plants.presentation.presenters.GardenPresenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
@PerActivity
public class NutrientDetailPresenter extends AbstractPresenter<NutrientDetailView> {

    private final static String TAG = GardenPresenter.class.getSimpleName();

    private final UseCase saveNutrientUseCase;

    @Inject
    public NutrientDetailPresenter(@Named("saveNutrient") UseCase saveNutrientUseCase) {
        this.saveNutrientUseCase = saveNutrientUseCase;
    }

    public void destroy() {
        this.saveNutrientUseCase.unsubscribe();
        this.view = null;
    }

    public void addNutrient(Nutrient nutrient) {
        this.saveNutrientUseCase.execute(nutrient, new SaveNutrientSubscriber());
    }

    private void notifyIfNutrientWasPersistedOrUpdated(Nutrient nutrient) {
        getView().notifyIfNutrientWasPersistedOrUpdated(nutrient);
    }

    private final class SaveNutrientSubscriber extends DefaultSubscriber<Nutrient> {

        @Override public void onCompleted() {
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Nutrient nutrient) {
            NutrientDetailPresenter.this.notifyIfNutrientWasPersistedOrUpdated(nutrient);
        }
    }
}
