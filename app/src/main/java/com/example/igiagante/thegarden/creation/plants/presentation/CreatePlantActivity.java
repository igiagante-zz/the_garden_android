package com.example.igiagante.thegarden.creation.plants.presentation;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.di.DaggerCreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.CreationBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class CreatePlantActivity extends BaseActivity implements ViewPager.OnPageChangeListener, HasComponent<CreatePlantComponent> {

    public static final int PICK_IMAGE_FROM_CAMERA_CODE = 23;
    public static final int PICK_IMAGE_FROM_GALLERY_CODE = 24;

    private CreatePlantComponent createPlantComponent;

    private PlantBuilder plantBuilder;

    @Bind(R.id.button_previous_id)
    Button mPreviousButton;

    @Bind(R.id.button_next_id)
    Button mNextButton;

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
        setContentView(R.layout.activity_create_plant);

        plantBuilder = new PlantBuilder();

        ButterKnife.bind(this);

        mPager = (ViewPager) findViewById(R.id.viewpager_create_plant);
        setupViewPager(mPager);
    }

    /**
     * Setup view pager with its adapter and buttons
     * @param viewPager view pager
     */
    private void setupViewPager(ViewPager viewPager) {

        currentPage = mPager.getCurrentItem();

        mPreviousButton.setOnClickListener(view -> moveToPreviousPage());
        mNextButton.setOnClickListener(view -> moveToNextPage());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), plantBuilder);

        //init fragments
        for (int i = 0; i < NUM_PAGES; i++) {
            adapter.getItem(i);
        }

        viewPager.setAdapter(adapter);
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
    public void onPageSelected(int position) {

        if(currentPage > position) {
            moveToPreviousPage();
        }

        if(currentPage < position) {
            moveToNextPage();
        }
    }

    /**
     * Notify to the active fragment about the movement to the previous page
     */
    private void moveToPreviousPage() {
        if(currentPage > 0) {
            getActiveFragment().moveToPreviousStep();
            currentPage -= 1;
            mPager.setCurrentItem(currentPage);
        }
    }

    /**
     * Notify to the active fragment about the movement to the next page
     */
    private void moveToNextPage() {
        if(currentPage < NUM_PAGES) {
            getActiveFragment().moveToNextStep();
            currentPage += 1;
            mPager.setCurrentItem(currentPage);
        }
    }

    /**
     * Get active fragment
     * @return fragment
     */
    private CreationBaseFragment getActiveFragment() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) mPager.getAdapter();
        CreationBaseFragment fragment = (CreationBaseFragment) adapter.getItem(currentPage);
        return fragment;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
