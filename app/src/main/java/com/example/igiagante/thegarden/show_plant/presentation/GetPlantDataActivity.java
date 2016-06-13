package com.example.igiagante.thegarden.show_plant.presentation;

import android.os.Bundle;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.show_plant.di.PlantDataComponent;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
public class GetPlantDataActivity extends BaseActivity implements HasComponent<PlantDataComponent> {

    private static final String PLANT_ID_KEY = "PLANT_ID";

    private String plantId;

    PlantDataComponent plantDataComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_plant_data_activity);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    public PlantDataComponent getComponent() {
        return plantDataComponent;
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(PLANT_ID_KEY, this.plantId);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.plantId = getIntent().getStringExtra(PLANT_ID_KEY);
            addFragment(R.id.container_plant_data_carousel_id, new GetPlantDataFragment());
        } else {
            this.plantId = savedInstanceState.getString(PLANT_ID_KEY);
        }
    }

    private void initializeInjector() {
        this.plantDataComponent = DaggerPlantDataComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
