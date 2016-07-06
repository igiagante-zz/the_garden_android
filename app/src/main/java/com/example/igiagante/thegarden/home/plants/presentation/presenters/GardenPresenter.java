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
    private final UseCase getGardenUseCase;
    private final UseCase saveGardenUseCase;
    private final UseCase deleteGardenUseCase;

    @Inject
    public GardenPresenter(@Named("gardens") UseCase getGardensUseCase,
                           @Named("getGarden") UseCase getGardenUseCase,
                           @Named("saveGarden") UseCase saveGardenUseCase,
                           @Named("deleteGarden") UseCase deleteGardenUseCase) {
        this.getGardensUseCase = getGardensUseCase;
        this.saveGardenUseCase = saveGardenUseCase;
        this.deleteGardenUseCase = deleteGardenUseCase;
        this.getGardenUseCase = getGardenUseCase;
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

    public void getGarden(String gardenId){
        getGardenUseCase.execute(gardenId, new GetGardenSubscriber());
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

    private void notifyIfGardenWasPersistedOrUpdated(Garden garden) {
        getView().notifyIfGardenWasPersistedOrUpdated(garden);
    }

    private void notifyIfGardenWasDeleted(String result) {
        getView().notifyIfGardenWasDeleted();
    }

    private void loadGarden(Garden garden) {
        getView().loadGarden(garden);
    }

    private final class SaveGardenSubscriber extends DefaultSubscriber<Garden> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override public void onNext(Garden garden) {
            GardenPresenter.this.notifyIfGardenWasPersistedOrUpdated(garden);
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

    private final class GetGardenSubscriber extends DefaultSubscriber<Garden> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Garden garden) {
            GardenPresenter.this.loadGarden(garden);
        }
    }
}
