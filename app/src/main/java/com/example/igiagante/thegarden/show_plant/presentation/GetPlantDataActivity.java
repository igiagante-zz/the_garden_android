package com.example.igiagante.thegarden.show_plant.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.CarouselFragment;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.home.plants.presentation.PlantsAdapter;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
public class GetPlantDataActivity extends BaseActivity implements
        CarouselFragment.OnDeleteImageInCarousel {

    private static final String PLANT_ID_KEY = "PLANT_ID";

    private String plantId;

    private PlantHolder mPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_plant_data_activity);

        this.initializeActivity(savedInstanceState);

        Intent intent = getIntent();

        if(intent != null) {
            mPlant = intent.getParcelableExtra(PlantsAdapter.SHOW_PLANT_KEY);
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.get_plant_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) findViewById(R.id.get_plant_toolbar_title)).setText(mPlant.getName());
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
            addFragment(R.id.container_plant_data_id, new GetPlantDataFragment());
        } else {
            this.plantId = savedInstanceState.getString(PLANT_ID_KEY);
        }
    }

    @Override
    public void deleteImageInCarousel(int position) {

    }

    public PlantHolder getPlant() {
        return mPlant;
    }
}
