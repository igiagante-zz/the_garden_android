package com.example.igiagante.thegarden.creation.plants.presentation.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.igiagante.thegarden.creation.plants.presentation.fragment.CarouselFragment;

/**
 * @author Ignacio Giagante, on 23/5/16.
 */
public class CarouselAdapter extends FragmentPagerAdapter {

    private String [] mUrlsImages;

    public CarouselAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CarouselFragment.newInstance(mUrlsImages[position]);
    }

    @Override
    public int getCount() {
        return 0;
    }

    public void setUrlsImages(String [] urlsImages) {
        this.mUrlsImages = urlsImages;
    }
}
