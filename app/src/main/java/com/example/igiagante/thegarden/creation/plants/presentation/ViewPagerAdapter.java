package com.example.igiagante.thegarden.creation.plants.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.igiagante.thegarden.creation.plants.presentation.fragment.AttributesFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.DescriptionFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.FlavorGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.MainDataFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.PhotoGalleryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Giagante, on 11/5/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<String> mFragmentTitleList = new ArrayList<>();

    private FragmentManager mFragmentManager;
    private PlantBuilder builder;

    public ViewPagerAdapter(FragmentManager manager, PlantBuilder plantBuilder) {
        super(manager);
        this.mFragmentManager = manager;
        this.builder = plantBuilder;
    }

    @Override
    public Fragment getItem(int position) {
        return getInstanceFragment(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    /**
     * Depend on the positin at the view pager, it will ask for an specific fragment instance
     * @param position pager's position
     * @return fragment
     */
    private Fragment getInstanceFragment(int position) {

        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new MainDataFragment();
                break;
            case 1:
                fragment = new PhotoGalleryFragment();
                break;
            case 2:
                fragment = new FlavorGalleryFragment();
                break;
            case 3:
                fragment = new AttributesFragment();
                break;
            case 4:
                fragment = new DescriptionFragment();
                break;
            default:
                fragment = new Fragment();
                break;
        }
        return fragment;
    }
}
