package com.example.igiagante.thegarden.creation.plants.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.PhotoGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.usecase.GetImagesUseCase;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author igiagante on 11/5/16.
 */
@PerActivity
public class PhotoGalleryPresenter extends AbstractPresenter<PhotoGalleryFragment> {

    private final UseCase getImagesUserCase;

    /**
     * Reference to the view (mvp)
     */
    private WeakReference<PhotoGalleryFragment> mView;

    @Inject
    public PhotoGalleryPresenter(@Named("images") UseCase getImagesUserCase) {
        this.getImagesUserCase = getImagesUserCase;
    }

    public void destroy() {
        this.getImagesUserCase.unsubscribe();
        this.mView = null;
    }

    public WeakReference<PhotoGalleryFragment> getView() {
        return mView;
    }

    public void setView(@NonNull WeakReference<PhotoGalleryFragment> mView) {
        this.mView = mView;
    }

    public void getImagesList(Collection<String> imagesPathFiles) {
        this.getImagesUserCase.execute(imagesPathFiles, new PhotoGallerySubscriber());
    }

    private void addImagesToBuilderInView(Collection<Image> images) {
        this.mView.get().addImagesToBuilder(images);
    }

    private final class PhotoGallerySubscriber extends DefaultSubscriber<List<Image>> {

        @Override public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<Image> images) {
            PhotoGalleryPresenter.this.addImagesToBuilderInView(images);
        }
    }
}
