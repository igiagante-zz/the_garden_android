package com.example.igiagante.thegarden.show_plant.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.core.ui.CirclePageIndicator;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.CarouselAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.ViewPagerAdapter;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.show_plant.adapters.PlantFlavorAdapter;
import com.example.igiagante.thegarden.show_plant.adapters.PlantPlagueAdapter;
import com.example.igiagante.thegarden.show_plant.di.PlantDataComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
public class GetPlantDataFragment extends BaseFragment implements GetPlantDataView {

    @Inject
    GetPlantDataPresenter getPlantDataPresenter;

    @Bind(R.id.plant_data_plagues_recycle_view_id)
    RecyclerView mPlaguesRecycleView;

    @Bind(R.id.plant_data_flavors_recycle_view_id)
    RecyclerView mFlavorsRecycleView;

    @Bind(R.id.plant_data_viewpager_id)
    ViewPager mViewPagerPhotos;

    private CirclePageIndicator mIndicator;
    private CarouselAdapter mAdapter;

    private PlantPlagueAdapter plagueAdapter;

    private PlantFlavorAdapter flavorAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(PlantDataComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.get_plant_data_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        // setup viewpager
        mIndicator = (CirclePageIndicator) fragmentView.findViewById(R.id.plant_data_indicator_id);

        mAdapter = new CarouselAdapter(getActivity().getSupportFragmentManager());
        mViewPagerPhotos.setAdapter(mAdapter);

        //add circle indicator
        mIndicator.setViewPager(mViewPagerPhotos);
        setupCircleIndicator();

        LinearLayoutManager layoutManagerPlagues = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerFlavors = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mPlaguesRecycleView.setLayoutManager(layoutManagerPlagues);
        mFlavorsRecycleView.setLayoutManager(layoutManagerFlavors);

        plagueAdapter = new PlantPlagueAdapter(getContext());
        mPlaguesRecycleView.setAdapter(plagueAdapter);

        flavorAdapter = new PlantFlavorAdapter(getContext());
        mFlavorsRecycleView.setAdapter(flavorAdapter);

        ViewPager viewPager = (ViewPager) fragmentView.findViewById(R.id.show_plant_attributes_viewpager_id);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) fragmentView.findViewById(R.id.show_plant_tab_layout_attributes);
        tabLayout.setupWithViewPager(viewPager);

        loadPlantData();

        return fragmentView;
    }

    /**
     * Add style to circle indicator
     * TODO - check if this can be done through xml
     */
    private void setupCircleIndicator() {
        final float density = getResources().getDisplayMetrics().density;
        mIndicator.setFillColor(R.color.gray);
        mIndicator.setStrokeColor(R.color.gray);
        mIndicator.setStrokeWidth(2);
        mIndicator.setRadius(6 * density);
    }

    private void setupViewPager(ViewPager viewPager) {

        PlantViewPagerAdapter adapter = new PlantViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new AttributeDataFragment(), "Effects");
        adapter.addFragment(new AttributeDataFragment(), "Medicinal");
        adapter.addFragment(new AttributeDataFragment(), "Symptoms");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getPlantDataPresenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getPlantDataPresenter.destroy();
    }

    private void loadPlantData() {
        PlantHolder mPlant = ((GetPlantDataActivity) getActivity()).getPlant();
        ArrayList<String> urls = getImagesFilesPaths(mPlant.getImages());
        mAdapter.setUrlsImages(urls);

        loadPlantPlagues(mPlant.getPlagues());
        loadPlantFlavors(mPlant.getFlavors());
    }

    private void loadPlantPlagues(List<Plague> plagues) {
        if (plagues != null && !plagues.isEmpty()) {
            plagueAdapter.setPlagues(plagues);
        }
    }

    private void loadPlantFlavors(List<Flavor> flavors) {
        if (flavors != null && !flavors.isEmpty()) {
            flavorAdapter.setFlavors(flavors);
        }
    }

    @Override
    public void loadPlantData(Plant plant) {

    }

    /**
     * Get all the images path from the parcelable image list.
     *
     * @param images Images
     * @return paths images folder path
     */
    private ArrayList<String> getImagesFilesPaths(List<Image> images) {

        ArrayList<String> paths = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            paths.add(images.get(i).getThumbnailUrl());
        }

        return paths;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    class PlantViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public PlantViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
