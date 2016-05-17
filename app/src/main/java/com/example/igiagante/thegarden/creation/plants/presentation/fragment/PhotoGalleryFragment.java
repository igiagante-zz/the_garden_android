package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.app.Dialog;
import android.content.Context;
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
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.presentation.GalleryAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenter.PhotoGalleryPresenter;
import com.example.igiagante.thegarden.home.plants.di.PlantComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class PhotoGalleryFragment extends CreationBaseFragment implements IView {

    public final static int PICK_IMAGE_CODE_CODE = 2345;

    @Bind(R.id.recycler_view_plant_photo_gallery)
    RecyclerView mGallery;

    @Inject
    PhotoGalleryPresenter mPhotoGalleryPresenter;

    private List<String> filesPaths;
    private GalleryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void createImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_image_source_tittle)
                .setItems(R.array.image_source_array, (dialog, which) -> {
                            if (which == 0) {
                                mPhotoGalleryPresenter.pickImageFromCamera();
                            } else {
                                mPhotoGalleryPresenter.pickImages();
                            }
                        }
                );
        builder.create().show();
    }

    public void loadImages(List<String> filesPaths) {
        this.filesPaths = filesPaths;
        mGallery.setVisibility(View.VISIBLE);
        mAdapter.setImagesPath(filesPaths);
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
}
