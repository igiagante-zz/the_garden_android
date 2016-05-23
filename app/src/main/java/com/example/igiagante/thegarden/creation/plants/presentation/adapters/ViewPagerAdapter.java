package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
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
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ViewPager mViewPager;

    private String [] titles = {};

    public ViewPagerAdapter(FragmentManager manager, Context context, ViewPager viewPager) {
        super(manager);
        titles = context.getResources().getStringArray(R.array.view_pager_fragment_title);
        this.mViewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return getInstanceFragment(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTitleByPosition(position);
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

        mViewPager.addOnPageChangeListener((CreationBaseFragment)fragment);

        //set title
        ((BaseFragment)fragment).setTitle(getTitleByPosition(position));

        return fragment;
    }

    private String getTitleByPosition(int position) {
        return titles[position];
    }
}
