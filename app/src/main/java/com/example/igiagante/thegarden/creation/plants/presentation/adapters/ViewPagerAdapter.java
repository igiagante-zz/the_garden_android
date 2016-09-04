package com.example.igiagante.thegarden.creation.plants.presentation.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantViewPager;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.AttributesFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.CreationBaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.DescriptionFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.FlavorGalleryFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.MainDataFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.PhotoGalleryFragment;

/**
 * Adapter that provides the fragments for the view pager
 *
 * {@link com.example.igiagante.thegarden.creation.plants.presentation.CarouselActivity#mPager}
 *
 * @author Ignacio Giagante, on 11/5/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_PAGES = 5;

    private SparseArray<Fragment> registeredFragments = new SparseArray<>(NUMBER_OF_PAGES);

    private CreatePlantViewPager mViewPager;

    private String [] titles = {};

    private Context mContext;

    public ViewPagerAdapter(FragmentManager manager, Context context, CreatePlantViewPager viewPager) {
        super(manager);
        this.mContext = context;
        this.titles = context.getResources().getStringArray(R.array.view_pager_fragment_title);
        this.mViewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return getInstanceFragment(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
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

        ((CreationBaseFragment)fragment)
                .getValidationMessageObservable().subscribe (validationMessage -> {
            if(validationMessage.getError()) {
                mViewPager.setPagingEnabled(false);
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                Toast.makeText(mContext, validationMessage.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                mViewPager.setPagingEnabled(true);
            }
        });

        ((CreationBaseFragment)fragment)
                .getEnableViewPagerObservable().subscribe (enable -> {
            if(enable) {
                mViewPager.setPagingEnabled(true);
            } else {
                Toast.makeText(mContext, " Some fields are empty ", Toast.LENGTH_SHORT).show();
                mViewPager.setPagingEnabled(false);
            }
        });

        mViewPager.addOnPageChangeListener((CreationBaseFragment)fragment);

        //set title
        ((BaseFragment)fragment).setTitle(getTitleByPosition(position));

        return fragment;
    }

    private String getTitleByPosition(int position) {
        return titles[position];
    }
}
