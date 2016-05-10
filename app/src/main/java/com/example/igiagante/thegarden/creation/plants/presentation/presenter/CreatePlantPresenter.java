package com.example.igiagante.thegarden.creation.plants.presentation.presenter;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.CreatePlantPhotoGalleryFragment;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author igiagante on 10/5/16.
 */
public class CreatePlantPresenter  extends AbstractPresenter<IView> {

    private WeakReference<CreatePlantPhotoGalleryFragment> mView;

    private ArrayList<File> imagesFiles;

    @Inject
    public CreatePlantPresenter() {
    }

    public WeakReference<CreatePlantPhotoGalleryFragment> getView() {
        return mView;
    }

    public void setView(@NonNull WeakReference<CreatePlantPhotoGalleryFragment> mView) {
        this.mView = mView;
    }

    public ArrayList<File> getImagesFiles() {
        return imagesFiles;
    }

    public void setImagesFiles(ArrayList<File> imagesFiles) {
        this.imagesFiles = imagesFiles;
    }
}
