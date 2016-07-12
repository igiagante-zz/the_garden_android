package com.example.igiagante.thegarden.creation.nutrients.presentation;

import android.os.Bundle;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.nutrients.di.DaggerNutrientsComponent;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientsComponent;

import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientActivity extends BaseActivity implements HasComponent<NutrientsComponent> {

    private NutrientsComponent nutrientComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrient_list_activity);
        ButterKnife.bind(this);

        initializeInjector();
    }

    private void initializeInjector() {
        this.nutrientComponent = DaggerNutrientsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public NutrientsComponent getComponent() {
        return this.nutrientComponent;
    }
}
