package com.example.igiagante.thegarden.home.plants.presentation.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.home.charts.presentation.ChartsFragment;
import com.example.igiagante.thegarden.home.irrigations.presentation.IrrigationsFragment;
import com.example.igiagante.thegarden.home.plants.presentation.PlantListFragment;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 6/7/16.
 */
public class GardenViewPagerAdapter extends FragmentPagerAdapter {

    private static final String PLANTS_TAG = "plants";

    private Context mContext;
    private FragmentManager fragmentManager;
    private Garden garden;
    private String [] titles = {};

    public GardenViewPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        this.fragmentManager = manager;
        this.mContext = context;
        titles = context.getResources().getStringArray(R.array.garden_view_pager_fragment_title);
    }

    @Override
    public Fragment getItem(int position) {
        return getInstanceFragment(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTitleByPosition(position);
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
        /*
        PlantListFragment plantListFragment = (PlantListFragment)fragmentManager.findFragmentByTag(PLANTS_TAG);
        plantListFragment.setPlants((ArrayList<Plant>) garden.getPlants()); */
        notifyDataSetChanged();
    }

    private String getTitleByPosition(int position) {
        return titles[position];
    }

    /**
     * Depend on the position at the view pager, it will ask for an specific fragment instance
     * @param position pager's position
     * @return fragment
     */
    private Fragment getInstanceFragment(int position) {

        Fragment fragment;

        switch (position) {
            case 0:
                fragment = PlantListFragment.newInstance((ArrayList<Plant>) garden.getPlants());
                /*
                fragmentManager.beginTransaction()
                        .add(android.R.id.content, fragment, PLANTS_TAG)
                        .commit();*/
                break;
            case 1:
                fragment = new IrrigationsFragment();
                break;
            case 2:
                fragment = new ChartsFragment();
                break;
            default:
                fragment = new Fragment();
                break;
        }

        return fragment;
    }
}