package com.example.igiagante.thegarden.home.irrigations.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.repository.network.Settings;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.irrigations.presentation.holders.NutrientHolder;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationDetailView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
@PerActivity
public class IrrigationDetailPresenter extends AbstractPresenter<IrrigationDetailView> {

    private final static String TAG = IrrigationDetailPresenter.class.getSimpleName();

    private final UseCase saveIrrigationUseCase;
    private final UseCase getNutrientsUseCase;
    private final UseCase saveGardenUseCase;

    @Inject
    public IrrigationDetailPresenter(@Named("saveIrrigation") UseCase saveIrrigationUseCase,
                                     @Named("getNutrients") UseCase getNutrientsUseCase,
                                     @Named("saveGarden") UseCase saveGardenUseCase) {
        this.saveIrrigationUseCase = saveIrrigationUseCase;
        this.getNutrientsUseCase = getNutrientsUseCase;
        this.saveGardenUseCase = saveGardenUseCase;
    }

    public void destroy() {
        this.saveIrrigationUseCase.unsubscribe();
        this.getNutrientsUseCase.unsubscribe();
        this.view = null;
    }

    public void saveIrrigation(Irrigation irrigation) {
        removeDomainFromNutrientsImages(irrigation.getDose().getNutrients());
        this.saveIrrigationUseCase.execute(irrigation, new SaveIrrigationSubscriber());
    }

    public void getNutrients() {
        this.getNutrientsUseCase.execute(null, new NutrientsSubscriber());
    }

    public void updateGarden(Garden garden) {
        this.saveGardenUseCase.execute(garden, new UpdateGardenSubscriber());
    }

    private void loadNutrients(List<NutrientHolder> nutrients) {
        getView().loadNutrients(nutrients);
    }

    private void notifyIfIrrigationWasPersistedOrUpdated(Irrigation irrigation) {
        getView().notifyIfIrrigationWasPersistedOrUpdated(irrigation);
    }

    private void notifyIfGardenWasUpdated(Garden garden) {
        getView().notifyIfGardenWasUpdated(garden);
    }

    private final class SaveIrrigationSubscriber extends DefaultSubscriber<Irrigation> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Irrigation irrigation) {
            IrrigationDetailPresenter.this.notifyIfIrrigationWasPersistedOrUpdated(irrigation);
        }
    }

    private final class NutrientsSubscriber extends DefaultSubscriber<List<Nutrient>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override
        public void onNext(List<Nutrient> nutrients) {
            IrrigationDetailPresenter.this.loadNutrients(createNutrientHolderList(nutrients));
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
            IrrigationDetailPresenter.this.notifyIfGardenWasUpdated(garden);
        }
    }

    public ArrayList<NutrientHolder> createNutrientHolderList(List<Nutrient> nutrients) {
        ArrayList<NutrientHolder> nutrientHolders = new ArrayList<>();
        for (Nutrient nutrient : nutrients) {
            NutrientHolder nutrientHolder = new NutrientHolder();
            nutrientHolder.setModel(nutrient);
            nutrientHolders.add(nutrientHolder);
        }
        return nutrientHolders;
    }

    private void removeDomainFromNutrientsImages(List<Nutrient> nutrients) {
        for (Nutrient nutrient : nutrients) {
            removeDomainFromImages(nutrient);
        }
    }

    private void removeDomainFromImages(Nutrient nutrient) {
        for (Image image : nutrient.getImages()) {
            if (image.getUrl() != null && image.getUrl().contains("http")) {
                String[] parts = image.getUrl().split(Settings.DOMAIN);
                image.setUrl(parts[1]);
            }
            if (image.getThumbnailUrl() != null && image.getThumbnailUrl().contains("http")) {
                String[] parts = image.getThumbnailUrl().split(Settings.DOMAIN);
                image.setThumbnailUrl(parts[1]);
            }
        }
    }
}
