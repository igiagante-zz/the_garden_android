package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.presentation.GalleryAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class CreatePlantPhotoGalleryFragment extends Fragment implements IView {

    public final static int PICK_IMAGE_CODE_CODE = 2345;

    @Bind(R.id.recycler_view_plant_photo_gallery)
    RecyclerView mGallery;

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

        GalleryAdapter adapter = new GalleryAdapter(context(), () -> pickImages());
        mGallery.setAdapter(adapter);

        return containerView;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return context();
    }

    private void pickImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), CreatePlantPhotoGalleryFragment.PICK_IMAGE_CODE_CODE);
    }
}
