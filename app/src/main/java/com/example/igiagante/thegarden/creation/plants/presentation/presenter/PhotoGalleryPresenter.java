package com.example.igiagante.thegarden.creation.plants.presentation.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.PhotoGalleryFragment;
import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;
import com.fuck_boilerplate.rx_paparazzo.entities.Size;

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

    public void pickImageFromCamera() {
        RxPaparazzo.takeImage(mView.get())
                .size(Size.Small)
                .usingCamera()
                .subscribe(response -> {

                    if (response.resultCode() != Activity.RESULT_OK) {
                        response.targetUI().showUserCanceled();
                        return;
                    }

                    ArrayList<String> images = new ArrayList<>();
                    images.add(response.data());
                    response.targetUI().loadImages(images);
                });
    }

    public void pickImages() {

        RxPaparazzo.takeImages(mView.get())
                .crop()
                .size(Size.Small)
                .usingGallery()
                .subscribe(response -> {
                    if (response.resultCode() != Activity.RESULT_OK) {
                        response.targetUI().showUserCanceled();
                        return;
                    }

                    response.targetUI().loadImages(response.data());
                });
    }
}
