package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.FlavorHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.views.FlavorGalleryView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * This presenter has the logic related to the flavors gallery
 *
 * @author Ignacio Giagante, on 30/5/16.
 */
@PerActivity
public class FlavorGalleryPresenter extends AbstractPresenter<FlavorGalleryView> {

    private final UseCase getFlavorsUserCase;

    @Inject
    public FlavorGalleryPresenter(@Named("flavors") UseCase getFlavorsUserCase) {
        this.getFlavorsUserCase = getFlavorsUserCase;
    }

    public void destroy() {
        this.getFlavorsUserCase.unsubscribe();
        this.view = null;
    }

    /**
     * Load the flavor in the view
     * @param flavors list of flavors
     */
    public void addFlavorsInView(List<Flavor> flavors) {
        getView().loadFlavors(createFlavorHolderList(flavors));
    }

    /**
     * Transform a list of flavors to flavor holders
     * @param flavors list of flavors
     * @return list of flavor holders
     */
    private ArrayList<FlavorHolder> createFlavorHolderList(List<Flavor> flavors) {

        ArrayList<FlavorHolder> flavorHolders = new ArrayList<>();

        for(Flavor flavor : flavors) {
            FlavorHolder flavorHolder = new FlavorHolder();
            flavorHolder.setModel(flavor);
            flavorHolders.add(flavorHolder);
        }
        return flavorHolders;
    }

    /**
     * Get flavor list
     */
    public void getFlavors() {
        this.getFlavorsUserCase.execute(null, new FlavorGallerySubscriber());
    }

    private final class FlavorGallerySubscriber extends DefaultSubscriber<List<Flavor>> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<Flavor> flavors) {
            FlavorGalleryPresenter.this.addFlavorsInView(flavors);
        }
    }
}
