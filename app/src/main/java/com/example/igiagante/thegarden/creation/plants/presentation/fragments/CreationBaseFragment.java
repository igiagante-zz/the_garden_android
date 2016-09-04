package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Base Fragment class used to get some events in common for the fragments which are in the viewPager
 * {@link CreatePlantActivity#createPlantViewPager}
 *
 * @author Ignacio Giagante, on 20/5/16.
 */
public class CreationBaseFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    /**
     * Main model
     */
    protected Plant mPlant;

    /**
     * Indicate if a plant is being updated
     */
    protected boolean updatingPlant = false;

    /**
     * List of resources ids which identify each image
     */
    protected List<String> resourcesIds = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof CreatePlantActivity) {
            mPlant = ((CreatePlantActivity) getActivity()).getPlant();
            if (mPlant != null) {
                updatingPlant = true;
                loadResourcesIds(mPlant.getImages());
            }
        }
    }

    /**
     * Load the resources ids from each image in order to know which image was deleted, added or not.
     */
    protected void loadResourcesIds(List<Image> mImages) {
        for (Image image : mImages) {
            resourcesIds.add(image.getId());
        }
    }

    /**
     * Update images list from builder
     *
     * @param images   list of images
     * @param carousel indicates if the images come from the carousel
     */
    protected void updateImagesFromBuilder(Collection<Image> images, boolean carousel) {
        Plant.PlantBuilder builder = ((CreatePlantActivity) getActivity()).getPlantBuilder();
        builder.setUpdatingPlant(updatingPlant);
        builder.addImages((ArrayList<Image>) images, carousel);
        builder.addResourcesIds(resourcesIds);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        move();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void updateBuilder() {
        move();
    }

    /**
     * Notify to the builder that the fragment have some data for saving.
     */
    protected void move() {
    }

    /**
     * Update view with the corresponding data
     */
    protected void loadPlantDataForEdition(PlantHolder plantHolder) {
    }
}
