package com.example.igiagante.thegarden.creation.plants.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.creation.plants.presentation.fragment.AttributesFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.CreationBaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.DescriptionFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.FlavorGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.MainDataFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.PhotoGalleryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Giagante, on 11/5/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private final List<String> mFragmentTitleList = new ArrayList<>();

    private final SparseArray<Fragment> mFragments;
    private FragmentManager mFragmentManager;
    private PlantBuilder builder;


    public ViewPagerAdapter(FragmentManager manager, PlantBuilder plantBuilder) {
        this.mFragmentManager = manager;
        this.mFragments = new SparseArray<>(7);
        this.builder = plantBuilder;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        trans.remove(mFragments.get(position));
        trans.commit();
        mFragments.put(position, null);
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = getItem(position);
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        trans.add(container.getId(), fragment, "fragment:" + position);
        trans.commit();
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object fragment) {
        return ((Fragment) fragment).getView() == view;
    }

    /**
     * Get item (Fragment) from the view pager
     * @param position item id
     * @return fragment
     */
    public Fragment getItem(int position) {
        if (mFragments.get(position) == null) {
            CreationBaseFragment fragment = getInstanceFragment(position);
            fragment.setBuilder(builder);
            mFragments.put(position, fragment);
        }
        return mFragments.get(position);
    }


    /**
     * Depend on the positin at the view pager, it will ask for an specific fragment instance
     * @param position pager's position
     * @return fragment
     */
    private CreationBaseFragment getInstanceFragment(int position) {

        CreationBaseFragment fragment;

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
                fragment = new CreationBaseFragment();
                break;
        }
        return fragment;
    }

}
