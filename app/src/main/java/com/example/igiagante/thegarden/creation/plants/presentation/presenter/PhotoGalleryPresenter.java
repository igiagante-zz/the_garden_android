package com.example.igiagante.thegarden.creation.plants.presentation.presenter;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.view.PhotoGalleryView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 11/5/16.
 */
@PerActivity
public class PhotoGalleryPresenter extends AbstractPresenter<PhotoGalleryView> {

    private final UseCase getImagesUserCase;

    @Inject
    public PhotoGalleryPresenter(@Named("images") UseCase getImagesUserCase) {
        this.getImagesUserCase = getImagesUserCase;
    }

    public void destroy() {
        this.getImagesUserCase.unsubscribe();
        view = null;
    }

    public void getImagesList(Collection<String> imagesPathFiles) {
        this.getImagesUserCase.execute(imagesPathFiles, new PhotoGallerySubscriber());
    }

    private void addImagesToBuilderInView(Collection<Image> images) {
        getView().addImagesToBuilder(images);
    }

    private final class PhotoGallerySubscriber extends DefaultSubscriber<List<Image>> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Image> images) {
            PhotoGalleryPresenter.this.addImagesToBuilderInView(images);
        }
    }
}
