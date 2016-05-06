package com.example.igiagante.thegarden.creation.plants.presentation;

import android.os.Bundle;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.di.DaggerCreatePlantComponent;

/**
 * @author igiagante on 6/5/16.
 */
public class CreatePlantActivity extends BaseActivity implements HasComponent<CreatePlantComponent> {

    /**
     * Component used to inject {@link CreatePlantFragment}
     */
    private CreatePlantComponent createPlantComponent;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plant);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.create_plant_fragment_id, new CreatePlantFragment());
        }
    }

    private void initializeInjector() {
        this.createPlantComponent = DaggerCreatePlantComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public CreatePlantComponent getComponent() {
        return createPlantComponent;
    }
}
