package com.example.igiagante.thegarden.home.plants.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.home.plants.di.PlantComponent;
import com.example.igiagante.thegarden.core.domain.entity.Plant;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment
 * @author Ignacio Giagante, on 5/5/16.
 */
public class PlantListFragment extends BaseFragment implements PlantListView {

    @Inject
    PlantListPresenter plantListPresenter;

    @Inject
    PlantsAdapter plantsAdapter;

    @Bind(R.id.recycle_view_id)
    RecyclerView recyclerViewPlants;

    @Bind(R.id.add_new_plant_id)
    Button buttonAddPlant;

    public PlantListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(PlantComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.fragment_plant_list, container, false);
        ButterKnife.bind(this, fragmentView);

        this.recyclerViewPlants.setLayoutManager(new LinearLayoutManager(context()));
        this.recyclerViewPlants.setAdapter(plantsAdapter);

        buttonAddPlant.setOnClickListener(view -> startActivity(new Intent(getActivity(), CreatePlantActivity.class)));

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.plantListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.plantListPresenter.getPlantList();
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        recyclerViewPlants.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.plantListPresenter.destroy();
    }

    @Override
    public void renderPlantList(Collection<Plant> plants) {
        this.plantsAdapter.setPlantsCollection(plants);
    }


    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
