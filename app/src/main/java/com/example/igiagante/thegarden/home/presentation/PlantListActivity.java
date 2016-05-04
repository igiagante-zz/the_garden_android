package com.example.igiagante.thegarden.home.presentation;

import android.os.Bundle;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.home.di.DaggerPlantComponent;
import com.example.igiagante.thegarden.home.di.PlantComponent;

/**
 * Created by igiagante on 2/5/16.
 */
public class PlantListActivity extends BaseActivity implements HasComponent<PlantComponent> {

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
}
