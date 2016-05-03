package com.example.igiagante.thegarden.plants.getPlants;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.fragment.BaseFragment;
import com.example.igiagante.thegarden.plants.di.PlantComponent;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.views.PlantListView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by igiagante on 2/5/16.
 */
public class PlantListFragment extends BaseFragment implements PlantListView {

    @Inject
    PlantListPresenter plantListPresenter;
    @Inject
    PlantsAdapter plantsAdapter;

    @Bind(R.id.recycle_view_id)
    RecyclerView recyclerViewPlants;

    public PlantListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(PlantComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_plant_list, container, false);
        ButterKnife.bind(this, fragmentView);

        this.recyclerViewPlants.setLayoutManager(new LinearLayoutManager(context()));
        this.recyclerViewPlants.setAdapter(plantsAdapter);

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

    @Override public void onResume() {
        super.onResume();
        this.plantListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.plantListPresenter.pause();
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }
}
