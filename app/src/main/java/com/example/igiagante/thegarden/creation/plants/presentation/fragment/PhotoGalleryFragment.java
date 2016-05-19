package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.GalleryAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenter.PhotoGalleryPresenter;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.crosswall.photo.pick.PickConfig;
import rx.Observable;

/**
 * @author igiagante on 10/5/16.
 */
public class PhotoGalleryFragment extends CreationBaseFragment implements IView {

    @Bind(R.id.recycler_view_plant_photo_gallery)
    RecyclerView mGallery;

    @Inject
    PhotoGalleryPresenter mPhotoGalleryPresenter;

    private GalleryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.getComponent(CreatePlantComponent.class).inject(this);

        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.fragment_plant_gallery, container, false);
        ButterKnife.bind(this, containerView);

        mGallery.setHasFixedSize(true);

        //Two columns for portrait
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mGallery.setLayoutManager(manager);

        mGallery.setAdapter(mAdapter);

        return containerView;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new GalleryAdapter(getContext(), this::pickImages);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == PickConfig.PICK_REQUEST_CODE){
            ArrayList<String> folderPaths = data.getStringArrayListExtra(PickConfig.EXTRA_STRING_ARRAYLIST);
            Toast.makeText(getActivity(), "pick size:" + folderPaths.size(), Toast.LENGTH_SHORT).show();
            loadImages(folderPaths);
        }
    }

    private void takePicture() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, CreatePlantActivity.PICK_IMAGE_FROM_CAMERA_CODE);
    }

    private void takePhotosFromGallery() {
        new PickConfig.Builder(getContext(), this)
                .pickMode(PickConfig.MODE_MULTIP_PICK)
                .maxPickSize(10)
                .spanCount(3)
                .toolbarColor(R.color.colorPrimary)
                .build();
    }

    public void loadImages(List<String> foldersPaths) {
        mGallery.setVisibility(View.VISIBLE);
        mAdapter = new GalleryAdapter(getContext(), this::pickImages);
        mAdapter.setImagesPath(foldersPaths);
        mGallery.setAdapter(mAdapter);
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
