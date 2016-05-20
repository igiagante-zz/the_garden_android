package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;

import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;

/**
 * @author Ignacio Giagante, on 20/5/16.
 */
public class CreationBaseFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    protected Activity mActivity;

    protected int myPosition;

    public interface OnMove {
        void move(@CreatePlantActivity.FragmentIdentity String fragmentIdentity);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == myPosition) {
            setLastState();    
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    protected void setLastState(){}
}
