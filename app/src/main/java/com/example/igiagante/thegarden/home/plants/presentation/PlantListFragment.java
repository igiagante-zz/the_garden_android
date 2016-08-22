package com.example.igiagante.thegarden.home.plants.presentation;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.home.di.MainComponent;
import com.example.igiagante.thegarden.home.gardens.presentation.adapters.GardenViewPagerAdapter;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;
import com.example.igiagante.thegarden.home.plants.presentation.presenters.PlantListPresenter;
import com.example.igiagante.thegarden.home.plants.presentation.view.PlantListView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment
 *
 * @author Ignacio Giagante, on 5/5/16.
 */
public class PlantListFragment extends BaseFragment implements PlantListView, PlantsAdapter.OnDeletePlant {

    private final static String PLANTS_KEY = "PLANTS";

    @Inject
    PlantListPresenter plantListPresenter;

    @Inject
    PlantsAdapter plantsAdapter;

    @Bind(R.id.recycle_view_id)
    RecyclerView recyclerViewPlants;

    @Bind(R.id.progress_bar_plants)
    ProgressBar progressBar;

    @Bind(R.id.plants_add_new_plant_id)
    FloatingActionButton fab;

    private ArrayList<Plant> mPlants = new ArrayList<>();

    private GardenHolder mGarden;

    public static PlantListFragment newInstance(Garden garden) {
        PlantListFragment myFragment = new PlantListFragment();

        Bundle args = new Bundle();
        args.putParcelable(MainActivity.GARDEN_KEY, garden);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(MainComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.plant_list_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        Bundle args = getArguments();
        if (args != null) {
            mGarden = args.getParcelable(MainActivity.GARDEN_KEY);
            if (mGarden != null) {
                mPlants = (ArrayList<Plant>) mGarden.getModel().getPlants();
            }
        }

        if (savedInstanceState != null) {
            mPlants = savedInstanceState.getParcelableArrayList(PLANTS_KEY);
        }

        plantsAdapter = new PlantsAdapter(getContext());
        this.recyclerViewPlants.setLayoutManager(new LinearLayoutManager(context()));
        this.recyclerViewPlants.setAdapter(plantsAdapter);

        fab.setOnClickListener(view ->
                startActivityForResult(createIntentForCreatePlantActivity(),
                        MainActivity.REQUEST_CODE_CREATE_PLANT_ACTIVITY));

        plantsAdapter.setOnEditPlant((MainActivity) getActivity());

        plantsAdapter.setOnDeletePlant(this);

        //load plant list from garden
        this.plantsAdapter.setPlants(createPlantHolderList(mPlants));

        return fragmentView;
    }

    // TODO - Remove this after FAB has been implemented
    private Intent createIntentForCreatePlantActivity() {
        Intent intent = new Intent(getActivity(), CreatePlantActivity.class);
        intent.putExtra(MainActivity.GARDEN_KEY, mGarden);
        return intent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PLANTS_KEY, mPlants);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.plantListPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerViewPlants.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.plantListPresenter.destroy();
    }

    @Override
    public void showDeletePlantDialog(int position) {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.delete_plant_dialog_title)
                .setMessage(R.string.delete_plant_dialog_content)
                .setPositiveButton("Yes", (dialog, which) -> plantsAdapter.deletePlant(position))
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void deletePlant(String plantId) {
        this.plantListPresenter.deletePlant(plantId);
    }

    private void removePlantFromGardenModel(String plantId) {
        List<Plant> plants = this.mGarden.getModel().getPlants();

        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).getId().equals(plantId)) {
                plants.remove(i);
            }
        }
    }

    @Override
    public void notifyPlantWasDeleted(String plantId) {
        removePlantFromGardenModel(plantId);
        this.plantsAdapter.removePlant();
    }

    public void setGarden(GardenHolder mGarden) {
        if (progressBar != null) {
            this.progressBar.setVisibility(View.VISIBLE);
        }
        this.mGarden = mGarden;
        setPlants((ArrayList<Plant>) this.mGarden.getModel().getPlants());
    }

    public void setPlants(ArrayList<Plant> mPlants) {
        this.mPlants = mPlants;
        if (plantsAdapter != null) {
            this.plantsAdapter.setPlants(createPlantHolderList(mPlants));
        }
        if (progressBar != null) {
            this.progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Transform a list of plants to plant holders
     *
     * @param plants list of plants
     * @return list of plant holders
     */
    private ArrayList<PlantHolder> createPlantHolderList(Collection<Plant> plants) {

        ArrayList<PlantHolder> plantHolders = new ArrayList<>();

        for (Plant plant : plants) {
            PlantHolder plantHolder = new PlantHolder();
            plantHolder.setModel(plant);
            plantHolders.add(plantHolder);
        }
        return plantHolders;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
