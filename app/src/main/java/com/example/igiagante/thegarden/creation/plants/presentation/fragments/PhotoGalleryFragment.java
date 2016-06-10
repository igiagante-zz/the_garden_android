package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CarouselActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.GalleryAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.PhotoGalleryPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.views.PhotoGalleryView;
import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * @author Ignacio Giagante, on 10/5/16.
 */
public class PhotoGalleryFragment extends CreationBaseFragment implements PhotoGalleryView, GalleryAdapter.OnShowImages {

    /**
     * It's the request code used to start the carousel intent.
     */
    public static final int CAROUSEL_REQUEST_CODE = 23;

    @Bind(R.id.recycler_view_plant_photo_gallery)
    RecyclerView mGallery;

    @Inject
    PhotoGalleryPresenter mPhotoGalleryPresenter;

    private GalleryAdapter mAdapter;

    /**
     * The list of images which should share between the gallery and teh carousel
     */
    private List<Image> mImages = new ArrayList<>();

    /**
     * The list of files' paths from files which were added using the android's gallery
     */
    private List<String> imagesFilesPaths = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            Plant plant = savedInstanceState.getParcelable(CreatePlantActivity.PLANT_KEY);
            if(plant != null) {
                mImages = plant.getImages();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.getComponent(CreatePlantComponent.class).inject(this);

        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.plant_gallery_fragment, container, false);
        ButterKnife.bind(this, containerView);

        // ask to the activity if it has a plant for edition
        if(mPlant != null) {
            mImages = mPlant.getImages();
        }

        mGallery.setHasFixedSize(true);

        //Two columns for portrait
        GridLayoutManager manager;
        if(isLandScape()) {
            manager = new GridLayoutManager(getActivity(), 3);
        } else {
            manager = new GridLayoutManager(getActivity(), 2);
        }

        mGallery.setLayoutManager(manager);

        mAdapter = new GalleryAdapter(getContext(), this::pickImages, this::deleteImage, this::onShowImages);
        mAdapter.setImagesPath(getImagesFilesPaths(mImages));
        mGallery.setAdapter(mAdapter);

        return containerView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Plant plant = new Plant();
        plant.setImages(mImages);
        outState.putParcelable(CreatePlantActivity.PLANT_KEY, plant);
    }

    /**
     * This method should implemented by the button Add Image
     */
    private void pickImages() {
        createImagePickerDialog();
    }

    /**
     * Call the {@link GalleryAdapter.OnDeleteImage#deleteImage(int)} method. The list of images
     * is updated too.
     * @param positionSelected represent the image's position inside the image's list
     */
    private void deleteImage(int positionSelected) {
        mAdapter.deleteImage(positionSelected);
        this.mImages.remove(positionSelected);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mPhotoGalleryPresenter.destroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPhotoGalleryPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void onShowImages(int pictureSelected) {
        Plant plant = new Plant();
        plant.setImages(mImages);

        Intent intent = new Intent(getActivity(), CarouselActivity.class);
        intent.putExtra(CarouselActivity.PICTURE_SELECTED_KEY, pictureSelected);
        intent.putExtra(CreatePlantActivity.PLANT_KEY, plant);

        this.startActivityForResult(intent, CAROUSEL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CAROUSEL_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            if(data != null) {

                Plant plant = data.getParcelableExtra(CreatePlantActivity.PLANT_KEY);
                imagesFilesPaths = getImagesFilesPaths(plant.getImages());

                //update adapter gallery
                mAdapter.setImagesPath(imagesFilesPaths);

                //update images from builder
                ArrayList<Image> imagesFromFilesPaths = getImagesFromFilesPaths(imagesFilesPaths);
                this.mImages = imagesFromFilesPaths;
                updateImagesFromBuilder(imagesFromFilesPaths, true);
            }
        }
    }

    /**
     * Add images to builder selected by the user.
     */
    public void addImagesToBuilder(Collection<Image> images) {
        updateImagesFromBuilder(images, false);
        Toast.makeText(getContext(), "number of images added: " + images.size(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void move() {
        super.move();
        // double check in case some image was deleted. So, the builder needs to be updated.
        updateImagesFromBuilder(mImages, true);
    }

    /**
     * Update images list from builder
     * @param images list of images
     * @param carousel indicates if the images come from the carousel
     */
    private void updateImagesFromBuilder(Collection<Image> images, boolean carousel) {
        Plant.PlantBuilder builder = ((CreatePlantActivity)getActivity()).getPlantBuilder();
        builder.addImages(images, carousel);
    }

    private ArrayList<Image> getImagesFromFilesPaths(List<String> paths) {
        ArrayList<Image> images = new ArrayList<>();

        for(String path : paths) {
            Image image = new Image();
            image.setUrl(path);
            images.add(image);
        }
        return images;
    }

    /**
     * Get all the images path from the parcelable image list.
     * @param images Images
     * @return paths images folder path
     */
    private ArrayList<String> getImagesFilesPaths(List<Image> images) {
        ArrayList<String> paths = new ArrayList<>();

        for(Image image : images) {
            paths.add(image.getUrl());
        }
        return paths;
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
                .usingCamera()
                .subscribe(response -> {
                    if (response.resultCode() != Activity.RESULT_OK) {
                        response.targetUI().showUserCanceled();
                        return;
                    }

                    ArrayList<String> list = new ArrayList<>();
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
     * @param filesPaths files paths
     */
    public void loadImages(List<String> filesPaths) {

        //update list of images files paths
        this.imagesFilesPaths.addAll(filesPaths);

        //update gallery with new images
        mGallery.setVisibility(View.VISIBLE);
        mAdapter.addImagesPaths(filesPaths);

        //add images to the builder plant
        mPhotoGalleryPresenter.getImagesList(filesPaths);
    }

    public void showUserCanceled() {
        Toast.makeText(getActivity(), getString(R.string.user_canceled), Toast.LENGTH_SHORT).show();
    }
}
