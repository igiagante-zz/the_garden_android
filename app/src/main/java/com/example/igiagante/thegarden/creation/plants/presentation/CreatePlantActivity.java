package com.example.igiagante.thegarden.creation.plants.presentation;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.di.DaggerCreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.ViewPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class CreatePlantActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        HasComponent<CreatePlantComponent> {

    public static final String PLANT_KEY = "PLANT";
    public static final String CURRENT_PAGE_KEY = "CURRENT_PAGE";

    private CreatePlantComponent createPlantComponent;

    private PlantBuilder plantBuilder;

    @Bind(R.id.button_previous_id)
    Button mPreviousButton;

    @Bind(R.id.button_next_id)
    Button mNextButton;

    private Toolbar mToolbar;

    /**
     * The number of pages (wizard steps).
     */
    private static final int NUM_PAGES = 5;

    /**
     * The current page.
     */
    private int currentPage = 0;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        setContentView(R.layout.create_plant_activity);

        if(savedInstanceState != null){
            currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
        }

        plantBuilder = new PlantBuilder();

        ButterKnife.bind(this);

        mPager = (ViewPager) findViewById(R.id.viewpager_create_plant);
        setupViewPager(mPager);

        mToolbar = (Toolbar) findViewById(R.id.create_plant_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setToolbarTitle(mPager.getAdapter().getPageTitle(0).toString());
    }

    /**
     * Setup view pager with its adapter and buttons
     * @param viewPager view pager
     */
    private void setupViewPager(ViewPager viewPager) {

        mPager.setOffscreenPageLimit(5);
        mPager.setCurrentItem(currentPage);

        if(isLandScape()) {
            mPreviousButton.setText("");
            mNextButton.setText("");
        }

        mPreviousButton.setOnClickListener(view -> moveToPreviousPage());
        mNextButton.setOnClickListener(view -> moveToNextPage());

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
        if(position < currentPage) {
            moveToPreviousPage();
        }

        if(position > currentPage) {
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
        if(currentPage > 0) {
            currentPage -= 1;
            mPager.setCurrentItem(currentPage);
        }
    }

    /**
     * Notify to the active fragment about the movement to the next page
     */
    private void moveToNextPage() {
        if(currentPage < NUM_PAGES) {
            currentPage += 1;
            mPager.setCurrentItem(currentPage);
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

    public PlantBuilder getPlantBuilder() {
        return plantBuilder;
    }

    public void setToolbarTitle(String title) {
        ((TextView) findViewById(R.id.create_plant_toolbar_title)).setText(title);
    }
}
