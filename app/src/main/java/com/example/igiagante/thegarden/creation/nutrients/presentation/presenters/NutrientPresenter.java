package com.example.igiagante.thegarden.creation.nutrients.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientView;
import com.example.igiagante.thegarden.home.plants.presentation.presenters.GardenPresenter;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
@PerActivity
public class NutrientPresenter extends AbstractPresenter<NutrientView> {

    private final static String TAG = GardenPresenter.class.getSimpleName();

    private final UseCase getNutrientsUseCase;
    private final UseCase saveNutrientUseCase;

    @Inject
    public NutrientPresenter(@Named("getNutrients") UseCase getNutrientsUseCase,
                             @Named("saveNutrient") UseCase saveNutrientUseCase) {
        this.getNutrientsUseCase = getNutrientsUseCase;
        this.saveNutrientUseCase = saveNutrientUseCase;
    }

    public void destroy() {
        this.getNutrientsUseCase.unsubscribe();
        this.view = null;
    }

    public void loadNutrients() {
        this.getNutrientsUseCase.execute(null, new NutrientsSubscriber());
    }

    public void addNutrient(Nutrient nutrient) {
        this.saveNutrientUseCase.execute(nutrient, new SaveNutrientSubscriber());
    }

    private void notifyIfNutrientWasPersistedOrUpdated(Nutrient nutrient) {
        getView().notifyIfNutrientWasPersistedOrUpdated(nutrient);
    }

    private void showNutrients(List<Nutrient> nutrients) {
        getView().loadNutrients(nutrients);
    }

    private final class NutrientsSubscriber extends DefaultSubscriber<List<Nutrient>> {

        @Override public void onCompleted() {
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(List<Nutrient> nutrients) {
            NutrientPresenter.this.showNutrients(nutrients);
        }
    }

    private final class SaveNutrientSubscriber extends DefaultSubscriber<Nutrient> {

        @Override public void onCompleted() {
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Nutrient nutrient) {
            NutrientPresenter.this.notifyIfNutrientWasPersistedOrUpdated(nutrient);
        }
    }
}
