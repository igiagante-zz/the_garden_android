package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.di.CreationPlantModule;
import com.example.igiagante.thegarden.creation.plants.di.DaggerCreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.GalleryAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenter.PhotoGalleryPresenter;
import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * @author igiagante on 10/5/16.
 */
public class PhotoGalleryFragment extends CreationBaseFragment implements IView {

    @Bind(R.id.recycler_view_plant_photo_gallery)
    RecyclerView mGallery;

    @Inject
    PhotoGalleryPresenter mPhotoGalleryPresenter;

    private CreatePlantComponent createPlantComponent;

    private GalleryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getComponent(CreatePlantComponent.class).inject(this);

        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.fragment_plant_gallery, container, false);
        ButterKnife.bind(this, containerView);

        mGallery.setHasFixedSize(true);

        //Two columns for portrait
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mGallery.setLayoutManager(manager);

        mAdapter = new GalleryAdapter(getContext(), this::pickImages);
        mGallery.setAdapter(mAdapter);

        return containerView;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPhotoGalleryPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    /**
     * Add the images selected by the user.
     */
    public void addImagesToBuilder(Collection<Image> images) {
        builder.addImages(images);
        Toast.makeText(getContext(), "number of images added: " + images.size(), Toast.LENGTH_LONG).show();
    }

    /**
     * Create a dialog with a Chooser Options in order to take a picture or pick some images
     */
    private void createImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_image_source_tittle)
                .setItems(R.array.image_source_array, (dialog, which) -> {
                            if (which == 0) {
                                takePicture();
                            } else {
                                takePhotosFromGallery();
                            }
                        }
                );
        builder.create().show();
    }

    /**
     * Take pictures using RxPaparazzo lib.
     */
    private void takePicture() {
        RxPaparazzo.takeImage(this)
                .usingGallery()
                .subscribe(response -> {
                    if (response.resultCode() != Activity.RESULT_OK) {
                        response.targetUI().showUserCanceled();
                        return;
                    }

                    ArrayList<String> list = new ArrayList<String>();
                    list.add(response.data());
                    response.targetUI().loadImages(list);
                });
    }

    /**
     * Pick some images from gallery using RxPaparazzo lib.
     */
    private void takePhotosFromGallery() {
        RxPaparazzo.takeImages(this)
                .usingGallery()
                .subscribe(response -> {
                    if (response.resultCode() != Activity.RESULT_OK) {
                        response.targetUI().showUserCanceled();
                        return;
                    }
                    response.targetUI().loadImages(response.data());
                });
    }

    /**
     * Notify to the gallery's adapter about the files paths
     * @param filesPaths files path
     */
    public void loadImages(List<String> filesPaths) {
        mGallery.setVisibility(View.VISIBLE);
        mAdapter.setImagesPath(filesPaths);
        initializeInjector(filesPaths);
    }

    private void initializeInjector(Collection<String> imagesFilePath) {
        this.createPlantComponent = DaggerCreatePlantComponent.builder()
                .creationPlantModule(new CreationPlantModule(imagesFilePath))
                .build();
    }

    public CreatePlantComponent getComponent() {
        return createPlantComponent;
    }

    public void showUserCanceled() {
        Toast.makeText(getActivity(), getString(R.string.user_canceled), Toast.LENGTH_SHORT).show();
    }

    /**
     * This method should implemented by the button Add Image
     */
    private void pickImages() {
        createImagePickerDialog();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
