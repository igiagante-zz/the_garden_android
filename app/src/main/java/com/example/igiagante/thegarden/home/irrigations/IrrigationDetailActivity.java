package com.example.igiagante.thegarden.home.irrigations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.nutrients.presentation.fragments.NutrientDetailFragment;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.home.irrigations.di.DaggerIrrigationComponent;
import com.example.igiagante.thegarden.home.irrigations.di.IrrigationComponent;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationDetailFragment;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationsFragment;

import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public class IrrigationDetailActivity extends BaseActivity implements HasComponent<IrrigationComponent> {

    public static final String IRRIGATION_KEY = "IRRIGATION";

    private static final String FRAGMENT_IRRIGATION_DATA_TAG = "FRAGMENT_IRRIGATION_DATA";

    private IrrigationComponent irrigationComponent;

    private Irrigation mIrrigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();

        setContentView(R.layout.irrigation_detail_activity);
        ButterKnife.bind(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.irrigation_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String title = getString(R.string.activity_irrigation_detail);
        ((TextView) findViewById(R.id.irrigation_detail_toolbar_title)).setText(title);

        mIrrigation = getIntent().getParcelableExtra(IRRIGATION_KEY);

        Fragment irrigationFragment;

        if(mIrrigation != null) {
            irrigationFragment = IrrigationDetailFragment.newInstance(mIrrigation);
        } else {
            irrigationFragment = new IrrigationDetailFragment();
        }

        Garden garden = getIntent().getParcelableExtra(MainActivity.GARDEN_KEY);
        ((IrrigationDetailFragment)irrigationFragment).setGarden(garden);

        addFragment(R.id.irrigation_container_data, irrigationFragment, FRAGMENT_IRRIGATION_DATA_TAG);
    }

    @Override
    public IrrigationComponent getComponent() {
        return irrigationComponent;
    }

    private void initializeInjector() {
        this.irrigationComponent = DaggerIrrigationComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
