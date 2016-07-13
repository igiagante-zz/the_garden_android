package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;

/**
 * Base Fragment class used to get some events in common for the fragments which are in the viewPager
 * {@link CreatePlantActivity#mPager}
 *
 * @author Ignacio Giagante, on 20/5/16.
 */
public class CreationBaseFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    protected Plant mPlant;

    /**
     * Indicate that a plant is being updated
     */
    protected boolean updatingPlant = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof CreatePlantActivity) {
            mPlant = ((CreatePlantActivity)getActivity()).getPlant();
            if(mPlant != null) {
                updatingPlant = true;
            }
        }
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
    protected void move() {}

    /**
     * Update view with the corresponding data
     */
    protected void loadPlantDataForEdition(PlantHolder plantHolder) {}
}
