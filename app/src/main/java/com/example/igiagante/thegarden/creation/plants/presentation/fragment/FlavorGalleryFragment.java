package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

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
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.PlantBuilder;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.FlavorAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenter.FlavorGalleryPresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class FlavorGalleryFragment extends CreationBaseFragment implements IView, FlavorAdapter.OnAddFlavor {

    @Inject
    FlavorGalleryPresenter mFlavorGalleryPresenter;

    @Bind(R.id.recycler_view_flavors)
    RecyclerView mFlavors;

    private FlavorAdapter mAdapter;

    private ArrayList<Flavor> flavorsAdded = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            Plant plant = savedInstanceState.getParcelable(CreatePlantActivity.PLANT_KEY);
            if(plant != null) {
                flavorsAdded = (ArrayList<Flavor>) plant.getFlavors();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.flavors_fragment, container, false);
        this.getComponent(CreatePlantComponent.class).inject(this);
        ButterKnife.bind(this, containerView);

        mFlavors.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        mFlavors.setLayoutManager(manager);

        mAdapter = new FlavorAdapter(getContext(), this);
        mFlavors.setAdapter(mAdapter);

        // Get Flavor List
        mFlavorGalleryPresenter.getFlavors();

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
        mFlavors.setAdapter(null);
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
        Plant plant = new Plant();
        plant.setFlavors(flavorsAdded);
        outState.putParcelable(CreatePlantActivity.PLANT_KEY, plant);
    }

    @Override
    public void addFlavorId(int flavorPosition) {

        if(flavorsAdded == null) {
            flavorsAdded = new ArrayList<>();
        }

        Flavor flavor = mAdapter.getFlavors().get(flavorPosition);

        if(flavorsAdded.contains(flavor)) {
            flavorsAdded.remove(flavorPosition);
        } else {
            flavorsAdded.add(flavor);
        }
    }

    public void loadFlavors(List<Flavor> flavors) {
        mAdapter.setFlavors(flavors);
    }

    @Override
    protected void move() {
        PlantBuilder builder = ((CreatePlantActivity)getActivity()).getPlantBuilder();
        builder.addFlavors(flavorsAdded);
    }
}
