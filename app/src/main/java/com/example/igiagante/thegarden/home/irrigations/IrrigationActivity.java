package com.example.igiagante.thegarden.home.irrigations;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.home.irrigations.di.DaggerIrrigationsComponent;
import com.example.igiagante.thegarden.home.irrigations.di.IrrigationsComponent;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationsFragment;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public class IrrigationActivity extends BaseActivity implements HasComponent<IrrigationsComponent> {

    private static final String FRAGMENT_IRRIGATION_LIST_TAG = "FRAGMENT_IRRIGATION_LIST";

    private IrrigationsComponent irrigationsComponent;

    private IrrigationsFragment mIrrigationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
        setContentView(R.layout.irrigation_list_activity);

        mIrrigationListFragment = new IrrigationsFragment();
        addFragment(R.id.irrigation_list_container, mIrrigationListFragment);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.irrigation_list_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String title = getString(R.string.nutrient_list_title);
        ((TextView) findViewById(R.id.irrigation_list_toolbar_title)).setText(title);
    }

    private void initializeInjector() {
        this.irrigationsComponent = DaggerIrrigationsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public IrrigationsComponent getComponent() {
        return this.irrigationsComponent;
    }
}
