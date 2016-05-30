package com.example.igiagante.thegarden.creation.plants.presentation.presenter;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.FlavorGalleryFragment;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
@PerActivity
public class FlavorGalleryPresenter extends AbstractPresenter<IView> {

    private final UseCase getFlavorsUserCase;

    /**
     * Reference to the view (mvp)
     */
    private WeakReference<FlavorGalleryFragment> mView;

    @Inject
    public FlavorGalleryPresenter(@Named("flavors") UseCase getFlavorsUserCase) {
        this.getFlavorsUserCase = getFlavorsUserCase;
    }

    public void destroy() {
        this.getFlavorsUserCase.unsubscribe();
        this.mView = null;
    }

    public void addFlavorsInView(List<Flavor> flavors) {
        this.mView.get().loadFlavors(flavors);
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
