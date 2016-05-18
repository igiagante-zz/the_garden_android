package com.example.igiagante.thegarden.creation.plants.presentation.presenter;

import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.PhotoGalleryFragment;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author igiagante on 11/5/16.
 */
public class PhotoGalleryPresenter extends AbstractPresenter<PhotoGalleryFragment> {

    /**
     * Reference to the view (mvp)
     */
    private WeakReference<PhotoGalleryFragment> mView;

    private ArrayList<File> imagesFiles;

    @Inject
    public PhotoGalleryPresenter() {
    }

    public WeakReference<PhotoGalleryFragment> getView() {
        return mView;
    }

    public void setView(@NonNull WeakReference<PhotoGalleryFragment> mView) {
        this.mView = mView;
    }

    public ArrayList<File> getImagesFiles() {
        return imagesFiles;
    }

    public void setImagesFiles(ArrayList<File> imagesFiles) {
        this.imagesFiles = imagesFiles;
    }


}
