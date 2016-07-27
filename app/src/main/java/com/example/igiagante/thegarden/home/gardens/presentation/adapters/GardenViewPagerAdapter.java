package com.example.igiagante.thegarden.home.gardens.presentation.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.home.charts.presentation.ChartsFragment;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationsFragment;
import com.example.igiagante.thegarden.home.plants.presentation.PlantListFragment;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;

/**
 * @author Ignacio Giagante, on 6/7/16.
 */
public class GardenViewPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<>(3);

    private GardenHolder gardenHolder;
    private String [] titles = {};

    public GardenViewPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
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

    public void setGardenHolder(GardenHolder gardenHolder) {
        this.gardenHolder = gardenHolder;
        setDataFromModel(gardenHolder);
    }

    // TODO - Refactor
    private void setDataFromModel(GardenHolder gardenHolder) {

        this.gardenHolder = gardenHolder;

        PlantListFragment plantListFragment = (PlantListFragment) registeredFragments.get(0);

        if(plantListFragment == null) {
            plantListFragment = PlantListFragment.newInstance(gardenHolder.getModel());
            registeredFragments.put(0, plantListFragment);
        }
        plantListFragment.setGarden(gardenHolder);

        IrrigationsFragment irrigationsFragment = (IrrigationsFragment) registeredFragments.get(1);
        if(irrigationsFragment == null) {
            irrigationsFragment = IrrigationsFragment.newInstance(gardenHolder.getModel());
            registeredFragments.put(1, irrigationsFragment);
        }
        irrigationsFragment.setGarden(gardenHolder);
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
                fragment = new PlantListFragment();
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