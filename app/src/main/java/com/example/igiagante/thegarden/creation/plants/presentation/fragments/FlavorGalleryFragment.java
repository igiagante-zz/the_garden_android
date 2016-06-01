package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.PlantBuilder;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.FlavorAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.FlavorHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.FlavorGalleryPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.views.FlavorGalleryView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class FlavorGalleryFragment extends CreationBaseFragment implements FlavorGalleryView, FlavorAdapter.OnAddFlavor {

    public static final String FLAVORS_KEY = "FLAVORS";

    @Inject
    FlavorGalleryPresenter mFlavorGalleryPresenter;

    @Bind(R.id.recycler_view_flavors)
    RecyclerView mFlavorsRecycleView;

    private FlavorAdapter mAdapter;

    /**
     * List of mFlavors
     */
    private ArrayList<FlavorHolder> mFlavors = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            mFlavors = savedInstanceState.getParcelableArrayList(FLAVORS_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.flavors_fragment, container, false);
        this.getComponent(CreatePlantComponent.class).inject(this);
        ButterKnife.bind(this, containerView);

        mFlavorsRecycleView.setHasFixedSize(true);

        GridLayoutManager manager;
        if(isLandScape()) {
            manager = new GridLayoutManager(getActivity(), 4);
        } else {
            manager = new GridLayoutManager(getActivity(), 3);
        }

        mFlavorsRecycleView.setLayoutManager(manager);

        mAdapter = new FlavorAdapter(getContext(), this);
        mAdapter.setFlavors(mFlavors);
        mFlavorsRecycleView.setAdapter(mAdapter);

        // Get Flavor List
        if(mFlavors.isEmpty()) {
            mFlavorGalleryPresenter.getFlavors();
        }

        return containerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mFlavorGalleryPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mFlavorGalleryPresenter.destroy();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mFlavorsRecycleView.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save one model with all the mFlavors
        outState.putParcelableArrayList(FLAVORS_KEY, mFlavors);
    }

    @Override
    public void addFlavor(int flavorPosition) {

        if(mFlavors.get(flavorPosition).isSelected()) {
            mFlavors.get(flavorPosition).setSelected(false);
        } else {
            mFlavors.get(flavorPosition).setSelected(true);
        }
    }

    @Override
    public void loadFlavors(List<FlavorHolder> flavors) {
        this.mFlavors = (ArrayList<FlavorHolder>) flavors;
        mAdapter.setFlavors(mFlavors);
    }

    @Override
    protected void move() {
        PlantBuilder builder = ((CreatePlantActivity)getActivity()).getPlantBuilder();
        builder.addFlavors(createFlavorsSelectedList());
    }

    private ArrayList<Flavor>  createFlavorsSelectedList() {

        ArrayList<Flavor> flavors = new ArrayList<>();

        for (FlavorHolder holder : mFlavors) {
            if(holder.isSelected()) {
                Flavor flavor = holder.getModel();
                flavor.setSelected(holder.isSelected());
                flavors.add(flavor);
            }
        }

        return flavors;
    }
}
