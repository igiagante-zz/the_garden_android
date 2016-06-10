package com.example.igiagante.thegarden.home.plants.presentation;

import android.content.Intent;
import android.os.Bundle;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.home.plants.di.DaggerPlantComponent;
import com.example.igiagante.thegarden.home.plants.di.PlantComponent;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;

/**
 * @author giagante on 5/5/16.
 */
public class PlantListActivity extends BaseActivity implements HasComponent<PlantComponent>, PlantsAdapter.OnEditPlant {

    private PlantComponent plantComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new PlantListFragment());
        }
    }

    private void initializeInjector() {
        this.plantComponent = DaggerPlantComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public PlantComponent getComponent() {
        return plantComponent;
    }

    @Override
    public void editPlant(PlantHolder plantHolder) {
        startActivity(createIntentEditPlant(plantHolder));
    }

    private Intent createIntentEditPlant(PlantHolder plantHolder) {

        Intent intent = getIntent();
        Plant plant = plantHolder.getModel();

        if(null != plant) {
            intent.putExtra(CreatePlantActivity.PLANT_KEY, plant);
        }
        return intent;
    }
}
