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

    private final static String TAG = NutrientPresenter.class.getSimpleName();

    private final UseCase getNutrientsUseCase;
    private final UseCase deleteNutrientUseCase;
    private final UseCase saveNutrientUseCase;

    @Inject
    public NutrientPresenter(@Named("getNutrients") UseCase getNutrientsUseCase,
                             @Named("deleteNutrient") UseCase deleteNutrientUseCase,
                             @Named("saveNutrient") UseCase saveNutrientUseCase) {
        this.getNutrientsUseCase = getNutrientsUseCase;
        this.saveNutrientUseCase = saveNutrientUseCase;
        this.deleteNutrientUseCase = deleteNutrientUseCase;
    }

    public void destroy() {
        this.getNutrientsUseCase.unsubscribe();
        this.saveNutrientUseCase.unsubscribe();
        this.deleteNutrientUseCase.unsubscribe();
        this.view = null;
    }

    public void loadNutrients() {
        this.getNutrientsUseCase.execute(null, new NutrientsSubscriber());
    }

    public void deleteNutrient(String nutrientId) {
        this.deleteNutrientUseCase.execute(nutrientId, new DeleteNutrientSubscriber());
    }

    public void saveNutrient(Nutrient nutrient) {
        this.saveNutrientUseCase.execute(nutrient, new SaveNutrientSubscriber());
    }

    private void showNutrients(List<Nutrient> nutrients) {
        getView().loadNutrients(nutrients);
    }

    private void notifyIfNutrientWasDeleted(Integer result) {
        getView().notifyIfNutrientWasDeleted();
    }

    private void notifyIfNutrientWasPersistedOrUpdated(Nutrient nutrient) {
        getView().notifyIfNutrientWasPersistedOrUpdated(nutrient);
    }

    private final class NutrientsSubscriber extends DefaultSubscriber<List<Nutrient>> {

        @Override public void onCompleted() {
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override public void onNext(List<Nutrient> nutrients) {
            NutrientPresenter.this.showNutrients(nutrients);
        }
    }

    private final class DeleteNutrientSubscriber extends DefaultSubscriber<Integer> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Integer result) {
            NutrientPresenter.this.notifyIfNutrientWasDeleted(result);
        }
    }

    private final class SaveNutrientSubscriber extends DefaultSubscriber<Nutrient> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Nutrient nutrient) {
            NutrientPresenter.this.notifyIfNutrientWasPersistedOrUpdated(nutrient);
        }
    }
}
