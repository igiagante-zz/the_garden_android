package com.example.igiagante.thegarden.home.gardens.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.home.charts.presentation.ChartsFragment;
import com.example.igiagante.thegarden.home.gardens.presentation.GardenFragment;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationsFragment;
import com.example.igiagante.thegarden.home.plants.presentation.PlantListFragment;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;

/**
 * @author Ignacio Giagante, on 6/7/16.
 */
public class GardenViewPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<>(3);

    private String[] titles = {};

    private Garden garden;

    public GardenViewPagerAdapter(FragmentManager manager, Context context, Garden garden) {
        super(manager);
        this.garden = garden;
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
    public CharSequence getPageTitle(int position) {
        return getTitleByPosition(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public void setGardenHolder(@NonNull GardenHolder gardenHolder) {

        this.garden = gardenHolder.getModel();

        for (int i = 0; i < registeredFragments.size(); i++) {
            if (this.garden != null) {
                ((GardenFragment) registeredFragments.get(i)).setGarden(this.garden);
            }
        }
    }

    public void createFirstGardenMessage() {
        for (int i = 0; i < registeredFragments.size(); i++) {
            ((GardenFragment) registeredFragments.get(i)).createOneGardenFirst();
        }
    }

    private String getTitleByPosition(int position) {
        return titles[position];
    }

    /**
     * Depend on the position at the view pager, it will ask for an specific fragment instance
     *
     * @param position pager's position
     * @return fragment
     */
    private Fragment getInstanceFragment(int position) {

        Fragment fragment;

        switch (position) {
            case 0:
                fragment = PlantListFragment.newInstance(garden);
                break;
            case 1:
                fragment = IrrigationsFragment.newInstance(garden);
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