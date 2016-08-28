package com.example.igiagante.thegarden.creation.nutrients.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.nutrients.di.DaggerNutrientComponent;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientComponent;
import com.example.igiagante.thegarden.creation.nutrients.presentation.adapters.NutrientsAdapter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.fragments.NutrientListFragment;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientActivity extends BaseActivity implements HasComponent<NutrientComponent>,
        NutrientsAdapter.OnNutrientSelected, NutrientListFragment.OnAddNutrientListener {

    @Inject
    Session mSession;

    public static final int REQUEST_CODE_NUTRIENT_DETAILS = 33;

    private static final String FRAGMENT_NUTRIENT_LIST_TAG = "FRAGMENT_NUTRIENT_LIST";

    private NutrientComponent nutrientComponent;

    private NutrientListFragment mNutrientListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
        this.getComponent().inject(this);

        setContentView(R.layout.nutrient_list_activity);

        mNutrientListFragment = new NutrientListFragment();
        addFragment(R.id.nutrient_list_container, mNutrientListFragment, FRAGMENT_NUTRIENT_LIST_TAG);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.nutrient_list_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String title = getString(R.string.nutrient_list_title);
        ((TextView) findViewById(R.id.nutrient_list_toolbar_title)).setText(title);
    }

    private void initializeInjector() {
        this.nutrientComponent = DaggerNutrientComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public NutrientComponent getComponent() {
        return this.nutrientComponent;
    }

    @Override
    public void showNutrientDetails(Nutrient nutrient) {
        Intent intent = new Intent(this, NutrientDetailActivity.class);
        intent.putExtra(NutrientDetailActivity.NUTRIENT_KEY, nutrient);
        startActivityForResult(intent, REQUEST_CODE_NUTRIENT_DETAILS);
    }

    @Override
    public void startNutrientDetailActivity() {
        startActivityForResult(new Intent(this, NutrientDetailActivity.class), REQUEST_CODE_NUTRIENT_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_NUTRIENT_DETAILS && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Nutrient nutrient = data.getParcelableExtra(NutrientDetailActivity.NUTRIENT_KEY);
                nutrient.setUserId(mSession.getUser().getId());
                if (mNutrientListFragment != null) {
                    mNutrientListFragment.addNutrient(nutrient);
                }
            }
        }
    }
}
