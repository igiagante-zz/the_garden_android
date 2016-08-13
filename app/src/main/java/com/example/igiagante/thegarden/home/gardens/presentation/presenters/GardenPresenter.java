package com.example.igiagante.thegarden.home.gardens.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;
import com.example.igiagante.thegarden.home.gardens.presentation.view.GardenView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
@PerActivity
public class GardenPresenter extends AbstractPresenter<GardenView> {

    private final static String TAG = GardenPresenter.class.getSimpleName();

    private final UseCase getGetGardensByUserUseCase;
    private final UseCase getGardensUseCase;
    private final UseCase getGardenUseCase;
    private final UseCase saveGardenUseCase;
    private final UseCase deleteGardenUseCase;
    private final UseCase existGardenUseCase;

    private final UseCase updateUserUseCase;

    @Inject
    public GardenPresenter(@Named("gardens") UseCase getGardensUseCase,
                           @Named("getGardensByUser") UseCase getGetGardensByUserUseCase,
                           @Named("getGarden") UseCase getGardenUseCase,
                           @Named("saveGarden") UseCase saveGardenUseCase,
                           @Named("deleteGarden") UseCase deleteGardenUseCase,
                           @Named("existGarden") UseCase existGardenUseCase,
                           @Named("saveUser") UseCase saveUserUseCase) {

        this.getGardensUseCase = getGardensUseCase;
        this.getGetGardensByUserUseCase = getGetGardensByUserUseCase;
        this.saveGardenUseCase = saveGardenUseCase;
        this.deleteGardenUseCase = deleteGardenUseCase;
        this.getGardenUseCase = getGardenUseCase;
        this.existGardenUseCase = existGardenUseCase;

        this.updateUserUseCase = saveUserUseCase;
    }

    public void destroy() {
        this.getGardensUseCase.unsubscribe();
        this.getGardenUseCase.unsubscribe();
        this.saveGardenUseCase.unsubscribe();
        this.deleteGardenUseCase.unsubscribe();
        this.view = null;
    }

    public void getGardens(String username) {
        this.getGetGardensByUserUseCase.execute(username, new GetGardensByUserSubscriber());
    }

    public void saveGarden(Garden garden) {
        this.saveGardenUseCase.execute(garden, new SaveGardenSubscriber());
    }

    public void deleteGarden(String gardenId) {
        this.deleteGardenUseCase.execute(gardenId, new DeleteGardenSubscriber());
    }

    public void getGarden(String gardenId){
        this.getGardenUseCase.execute(gardenId, new GetGardenSubscriber());
    }

    public void existsGarden(String gardenName) {
        this.existGardenUseCase.execute(gardenName, new ExistsGardenSubscriber());
    }

    public void updateUser(User user) {
        this.updateUserUseCase.execute(user, new UpdateUserSubscriber());
    }

    private void showGardens(List<GardenHolder> gardens) {
        getView().loadGardens(gardens);
    }

    private void notifyIfGardenWasPersistedOrUpdated(Garden garden) {
        getView().notifyIfGardenWasPersistedOrUpdated(garden);
    }

    private void notifyIfGardenWasDeleted(Integer result) {
        getView().notifyIfGardenWasDeleted();
    }

    private void loadGarden(Garden garden) {
        getView().loadGarden(createGardenHolder(garden));
    }

    private void notifyIfGardenExists(boolean exists){
        getView().notifyIfGardenExists(exists);
    }

    private void notifyIfUserWasUpdated(User user) {
        getView().notifyIfUserWasUpdated(user);
    }

    // TODO - Refactor
    private final class GetGardensByUserSubscriber extends DefaultSubscriber<User> {

        @Override public void onCompleted() {
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        @Override public void onNext(User user) {
            GardenPresenter.this.showGardens(createGardenHolderList(user.getGardens()));
        }
    }

    /**
     * Create garden holder list
     * @param gardens Gardens
     * @return gardenHolders
     */
    private ArrayList<GardenHolder> createGardenHolderList(List<Garden> gardens) {

        ArrayList<GardenHolder> gardenHolders = new ArrayList<>();

        if(gardens != null) {
            for (int i = 0; i < gardens.size(); i++) {
                gardenHolders.add(createGardenHolder(gardens.get(i), i));
            }
        }

        return gardenHolders;
    }

    public GardenHolder createGardenHolder(Garden garden, int position) {
        GardenHolder gardenHolder = new GardenHolder();
        gardenHolder.setModel(garden);
        gardenHolder.setPosition(position);
        return gardenHolder;
    }

    private GardenHolder createGardenHolder(Garden garden) {
        GardenHolder gardenHolder = new GardenHolder();
        gardenHolder.setModel(garden);
        return gardenHolder;
    }

    private final class ExistsGardenSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(Boolean exist) {
            GardenPresenter.this.notifyIfGardenExists(exist);
        }
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

    private final class DeleteGardenSubscriber extends DefaultSubscriber<Integer> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(Integer result) {
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

    private final class UpdateUserSubscriber extends DefaultSubscriber<User> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
        }

        @Override public void onNext(User user) {
            GardenPresenter.this.notifyIfUserWasUpdated(user);
        }
    }
}
