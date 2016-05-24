package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

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
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CarouselActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.PlantBuilder;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.GalleryAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenter.PhotoGalleryPresenter;
import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * @author igiagante on 10/5/16.
 */
public class PhotoGalleryFragment extends CreationBaseFragment implements IView, GalleryAdapter.OnShowImages {

    public static final String PLANT_KEY = "PLANT";

    /**
     * It's the request code used to start the carousel intent.
     */
    public static final int CAROUSEL_REQUEST_CODE = 23;

    @Bind(R.id.recycler_view_plant_photo_gallery)
    RecyclerView mGallery;

    @Inject
    PhotoGalleryPresenter mPhotoGalleryPresenter;

    private GalleryAdapter mAdapter;

    private List<Image> mImages = new ArrayList<>();
    private List<String> imagesFilesPaths = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.getComponent(CreatePlantComponent.class).inject(this);

        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.plant_gallery_fragment, container, false);
        ButterKnife.bind(this, containerView);

        mGallery.setHasFixedSize(true);

        //Two columns for portrait
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mGallery.setLayoutManager(manager);

        mAdapter = new GalleryAdapter(getContext(), this::pickImages, this::deleteImage, this::onShowImages);
        mGallery.setAdapter(mAdapter);

        return containerView;
    }

    /**
     * This method should implemented by the button Add Image
     */
    private void pickImages() {
        createImagePickerDialog();
    }

    private void deleteImage(int positionSelected, String imagePath) {
        deleteImageFromCarouselImages(imagePath);
        mAdapter.deleteImage(positionSelected);
    }

    /**
     * Delete one image from the images list used by the carousel
     * @param imagePath The image path
     */
    private void deleteImageFromCarouselImages(String imagePath){

        for(int i = 0; i < mImages.size(); i++) {
            if(mImages.get(i).getUrl().equals(imagePath)) {
                mImages.remove(i);
            }
        }
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

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mPhotoGalleryPresenter.destroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onShowImages(int pictureSelected) {
        Plant plant = new Plant();
        plant.setImages(mImages);

        Intent intent = new Intent(getActivity(), CarouselActivity.class);
        intent.putExtra(CarouselActivity.PICTURE_SELECTED_KEY, pictureSelected);
        intent.putExtra(PLANT_KEY, plant);

        this.startActivityForResult(intent, CAROUSEL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CAROUSEL_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            if(data != null) {
                Plant plant = data.getParcelableExtra(PLANT_KEY);
                imagesFilesPaths = getImagesFilesPaths(plant.getImages());
                //update adapter gallery
                mAdapter.setImagesPath(imagesFilesPaths);
            }
        }
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
     * Add images to builder selected by the user.
     */
    public void addImagesToBuilder(Collection<Image> images) {
        this.mImages.addAll(images);
        PlantBuilder builder = ((CreatePlantActivity)getActivity()).getPlantBuilder();
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
     * @param filesPaths files path
     */
    public void loadImages(List<String> filesPaths) {
        this.imagesFilesPaths.addAll(filesPaths);
        mGallery.setVisibility(View.VISIBLE);
        mAdapter.addImagesPaths(filesPaths);
        mPhotoGalleryPresenter.getImagesList(filesPaths);
    }

    public void showUserCanceled() {
        Toast.makeText(getActivity(), getString(R.string.user_canceled), Toast.LENGTH_SHORT).show();
    }
}
