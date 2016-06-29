package com.example.igiagante.thegarden.creation.plants.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.core.presentation.FlowStepExecutor;
import com.example.igiagante.thegarden.core.presentation.FlowStepResolver;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.di.DaggerCreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.ViewPagerAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.DescriptionFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.SavePlantPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.views.SavePlantView;
import com.example.igiagante.thegarden.home.MainActivity;

import java.lang.ref.WeakReference;

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
     * Model to keep all the data related to the plant
     */
    private Plant mPlant;

    private Toolbar mToolbar;

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
    private ViewPager mPager;

    @Bind(R.id.edit_plant_button_back)
    Button mButtonBack;

    @Bind(R.id.edit_plant_button_save)
    Button mButtonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_plant_activity);

        this.initializeInjector();
        ButterKnife.bind(this);

        // set view for this presenter
        this.mSavePlantPresenter.setView(new WeakReference<>(this));

        // check if the activity was started in order to edit a plant
        mPlant = getIntent().getParcelableExtra(PLANT_KEY);
        plantBuilder = new Plant.PlantBuilder();
        if (mPlant != null) {
            plantBuilder.addPlantId(mPlant.getId());
        }

        if (savedInstanceState != null) {
            currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
        }

        mPager = (ViewPager) findViewById(R.id.viewpager_create_plant);
        setupViewPager(mPager);

        mToolbar = (Toolbar) findViewById(R.id.create_plant_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setToolbarTitle(mPager.getAdapter().getPageTitle(0).toString());

        mButtonBack.setOnClickListener(v -> finish());
        mButtonSave.setOnClickListener(v -> mSavePlantPresenter.savePlant(plantBuilder.build()));
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
    private void setupViewPager(ViewPager viewPager) {

        mPager.setOffscreenPageLimit(NUMBER_OF_PAGES);
        mPager.setCurrentItem(currentPage);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGE_KEY, currentPage);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setToolbarTitle(mPager.getAdapter().getPageTitle(position).toString());
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

    /**
     * Notify to the active fragment about the movement to the previous page
     */
    private void moveToPreviousPage() {
        if (currentPage > 0) {
            currentPage -= 1;
            mPager.setCurrentItem(currentPage);
        }
    }

    /**
     * Notify to the active fragment about the movement to the next page
     */
    private void moveToNextPage() {
        if (currentPage < NUMBER_OF_PAGES) {
            currentPage += 1;
            mPager.setCurrentItem(currentPage);
        }
    }

    @Override
    public CreatePlantComponent getComponent() {
        return createPlantComponent;
    }

    @Override
    public void notifyIfPlantWasPersisted(String plantId) {
        FlowStepExecutor flowStepExecutor = new FlowStepExecutor();
        flowStepExecutor.goToNextStep(null, MainActivity.class, this);
    }

    @Override
    public void notifyIfPlantWasUpdated(Plant plant) {

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

    @Override
    public void onSavePlant() {
        mSavePlantPresenter.savePlant(plantBuilder.build());
    }

    @Override
    public void goToNextStep(Bundle bundle, Class clazz) {
        startActivity(new Intent(this, clazz));
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
