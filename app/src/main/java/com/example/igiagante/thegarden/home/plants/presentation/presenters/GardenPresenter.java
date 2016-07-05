package com.example.igiagante.thegarden.home.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.plants.presentation.view.GardenView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
@PerActivity
public class GardenPresenter extends AbstractPresenter<GardenView> {

    private final static String TAG = GardenPresenter.class.getSimpleName();

    private final UseCase getGardensUseCase;
    private final UseCase saveGardenUseCase;
    private final UseCase deleteGardenUseCase;

    @Inject
    public GardenPresenter(@Named("gardens") UseCase getGardensUseCase,
                           @Named("saveGarden") UseCase saveGardenUseCase,
                           @Named("deleteGarden") UseCase deleteGardenUseCase) {
        this.getGardensUseCase = getGardensUseCase;
        this.saveGardenUseCase = saveGardenUseCase;
        this.deleteGardenUseCase = deleteGardenUseCase;
    }

    public void destroy() {
        this.getGardensUseCase.unsubscribe();
        this.view = null;
    }

    public void getGardens() {
        getGardensUseCase.execute(null, new GardenSubscriber());
    }

    public void saveGarden(Garden garden) {
        saveGardenUseCase.execute(garden, new SaveGardenSubscriber());
    }

    public void deleteGarden(String gardenId) {
        deleteGardenUseCase.execute(gardenId, new DeleteGardenSubscriber());
    }

    private void showGardens(List<Garden> gardens) {
        getView().loadGardens(gardens);
    }

    private final class GardenSubscriber extends DefaultSubscriber<List<Garden>> {

        @Override public void onCompleted() {
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(List<Garden> gardens) {
            GardenPresenter.this.showGardens(gardens);
        }
    }

    private void notifyIfGardenWasPersisted(String gardenId) {
        getView().notifyIfGardenWasPersisted(gardenId);
    }

    private void notifyIfGardenWasUpdated(Garden garden) {
        getView().notifyIfGardenWasUpdated(garden);
    }

    private void notifyIfGardenWasDeleted(String result) {
        getView().notifyIfGardenWasDeleted();
    }

    private final class SaveGardenSubscriber extends DefaultSubscriber<Object> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override public void onNext(Object object) {
            if(object instanceof String) {
                GardenPresenter.this.notifyIfGardenWasPersisted((String)object);
            } else {
                GardenPresenter.this.notifyIfGardenWasUpdated((Garden)object);
            }
        }
    }

    private final class DeleteGardenSubscriber extends DefaultSubscriber<String> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(String result) {
            GardenPresenter.this.notifyIfGardenWasDeleted(result);
        }
    }
}
