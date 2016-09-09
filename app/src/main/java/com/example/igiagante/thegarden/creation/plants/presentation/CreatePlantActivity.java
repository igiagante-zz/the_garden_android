package com.example.igiagante.thegarden.creation.plants.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.core.presentation.FlowStepExecutor;
import com.example.igiagante.thegarden.core.presentation.FlowStepResolver;
import com.example.igiagante.thegarden.creation.plants.di.components.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.di.components.DaggerCreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.ViewPagerAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.CreationBaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.DescriptionFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.SavePlantPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.views.SavePlantView;
import com.example.igiagante.thegarden.home.MainActivity;
import com.google.android.gms.analytics.HitBuilders;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class CreatePlantActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        HasComponent<CreatePlantComponent>,
        FlowStepResolver,
        DescriptionFragment.OnSavePlantListener,
        SavePlantView {

    public static final String PLANT_KEY = "PLANT";
    public static final String CURRENT_PAGE_KEY = "CURRENT_PAGE";

    private static final String TAG = CreatePlantActivity.class.getSimpleName();

    @Inject
    SavePlantPresenter mSavePlantPresenter;

    /**
     * Dagger component used to inject some dependencies
     */
    private CreatePlantComponent createPlantComponent;

    /**
     * This builder class will keep all the information related to the creation process plant
     */
    private Plant.PlantBuilder plantBuilder;

    /**
     * Where the plant belongs to
     */
    private Garden mGarden;

    /**
     * Model to keep all the data related to the plant
     */
    private Plant mPlant;

    /**
     * The number of pages (wizard steps).
     */
    private static final int NUMBER_OF_PAGES = 5;

    /**
     * The current page.
     */
    private int currentPage = 0;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private CreatePlantViewPager createPlantViewPager;

    @Bind(R.id.edit_plant_button_back)
    Button mButtonBack;

    @Bind(R.id.edit_plant_button_save)
    Button mButtonSave;

    @Bind(R.id.progress_bar_plant)
    ProgressBar mProgressBar;

    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_plant_activity);

        this.initializeInjector();
        ButterKnife.bind(this);

        tracker.setScreenName(TAG);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        // Create builder which will in charge of creating the plant
        plantBuilder = new Plant.PlantBuilder();

        // get garden info
        mGarden = getIntent().getParcelableExtra(MainActivity.GARDEN_KEY);
        if (mGarden != null) {
            plantBuilder.addGardenId(mGarden.getId());
        }

        // set view for this presenter
        this.mSavePlantPresenter.setView(new WeakReference<>(this));

        // check if the activity was started in order to edit a plant
        mPlant = getIntent().getParcelableExtra(PLANT_KEY);
        if (mPlant != null) {
            plantBuilder.addPlantId(mPlant.getId());
        }

        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
        }

        createPlantViewPager = (CreatePlantViewPager) findViewById(R.id.viewpager_create_plant);
        setupViewPager(createPlantViewPager);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.create_plant_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setToolbarTitle(createPlantViewPager.getAdapter().getPageTitle(0).toString());

        if (mPlant != null) {
            mButtonBack.setVisibility(View.VISIBLE);
            mButtonSave.setVisibility(View.VISIBLE);
            mButtonBack.setOnClickListener(v -> finish());
            mButtonSave.setOnClickListener(v -> {
                updateBuilder();
                mSavePlantPresenter.savePlant(plantBuilder.build());
            });
        }
    }

    private void initializeInjector() {
        this.createPlantComponent = DaggerCreatePlantComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        createPlantComponent.inject(this);
    }

    /**
     * Setup view pager with its adapter and buttons
     *
     * @param viewPager view pager
     */
    private void setupViewPager(CreatePlantViewPager viewPager) {
        createPlantViewPager.setOffscreenPageLimit(NUMBER_OF_PAGES);
        createPlantViewPager.setCurrentItem(currentPage);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this, viewPager);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    /**
     * If the user is editing a plant, it doesn't matter where the user is standing at the wizard.
     * After the user click the save button, it needs to walk through the fragments and verifies if
     * some data has been modified.
     */
    private void updateBuilder() {
        for (int i = 0; i < mViewPagerAdapter.getCount(); i++) {
            ((CreationBaseFragment) mViewPagerAdapter.getRegisteredFragment(i)).updateBuilder();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGE_KEY, currentPage);
    }

    @Override
    public void onBackPressed() {
        if (createPlantViewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            createPlantViewPager.setCurrentItem(createPlantViewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setToolbarTitle(createPlantViewPager.getAdapter().getPageTitle(position).toString());
        if (position < currentPage) {
            moveToPreviousPage();
        }

        if (position > currentPage) {
            moveToNextPage();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void showLoading() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Notify to the active fragment about the movement to the previous page
     */
    private void moveToPreviousPage() {
        if (currentPage > 0) {
            currentPage -= 1;
            createPlantViewPager.setCurrentItem(currentPage);
        }
    }

    /**
     * Notify to the active fragment about the movement to the next page
     */
    private void moveToNextPage() {
        if (currentPage < NUMBER_OF_PAGES) {
            currentPage += 1;
            createPlantViewPager.setCurrentItem(currentPage);
        }
    }

    @Override
    public CreatePlantComponent getComponent() {
        return createPlantComponent;
    }

    @Override
    public void onSavePlant() {
        Plant plant = plantBuilder.build();
        plant.setGardenId(mGarden.getId());
        if (checkInternet()) {
            this.mSavePlantPresenter.savePlant(plant);
        } else {
            showMessageNoInternetConnection();
        }
    }

    public void notifyIfPlantWasPersistedOrUpdated(Plant plant) {
        //update garden model
        List<Plant> plants = this.mGarden.getPlants();

        boolean plantUpdated = false;

        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).getId().equals(plant.getId())) {
                plants.set(i, plant);
                plantUpdated = true;
            }
        }

        if (!plantUpdated) {
            plants.add(plant);
        }

        this.mSavePlantPresenter.updateGarden(mGarden);
    }

    @Override
    public void notifyIfGardenWasUpdated(Garden garden) {
        goToMainActivity();
    }

    private void goToMainActivity() {
        mProgressBar.setVisibility(View.GONE);
        FlowStepExecutor flowStepExecutor = new FlowStepExecutor();
        flowStepExecutor.goToNextStep(null, MainActivity.class, this);
    }

    @Override
    public void goToNextStep(Bundle bundle, Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(MainActivity.GARDEN_KEY, mGarden);
        setResult(Activity.RESULT_OK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mSavePlantPresenter.destroy();
        ButterKnife.unbind(this);
    }

    @Override
    public Context context() {
        return getApplicationContext();
    }

    public Plant getPlant() {
        return mPlant;
    }

    /**
     * Get Plant Builder
     *
     * @return plantBuilder
     */
    public Plant.PlantBuilder getPlantBuilder() {
        return plantBuilder;
    }

    public void setToolbarTitle(String title) {
        ((TextView) findViewById(R.id.create_plant_toolbar_title)).setText(title);
    }
}
