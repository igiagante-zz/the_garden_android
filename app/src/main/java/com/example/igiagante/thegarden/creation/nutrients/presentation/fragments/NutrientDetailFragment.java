package com.example.igiagante.thegarden.creation.nutrients.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientDetailPresenter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientDetailView;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.GalleryAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientDetailFragment extends BaseFragment implements NutrientDetailView {

    @Inject
    NutrientDetailPresenter nutrientDetailPresenter;

    @Bind(R.id.nutrient_deatail_recycle_view)
    RecyclerView mGallery;

    private GalleryAdapter mAdapter;

    /**
     * List of images which should share between the gallery and teh carousel
     */
    private List<Image> mImages = new ArrayList<>();

    /**
     * List of files' paths from files which were added using the android's gallery
     */
    private List<String> imagesFilesPaths = new ArrayList<>();

    /**
     * List of resources ids which identify each image
     */
    private List<String> resourcesIds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
       // this.getComponent(NutrientsComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.plant_list_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        //Two columns for portrait
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mGallery.setLayoutManager(manager);

        this.mGallery.setLayoutManager(new LinearLayoutManager(context()));

        //load images nutrients

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.nutrientDetailPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.nutrientDetailPresenter.destroy();
    }

    @Override
    public void notifyIfNutrientWasPersistedOrUpdated(Nutrient nutrient) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }
}
