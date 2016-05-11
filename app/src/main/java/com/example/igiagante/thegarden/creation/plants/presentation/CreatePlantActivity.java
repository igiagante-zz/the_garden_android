package com.example.igiagante.thegarden.creation.plants.presentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.CreationBaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.MainDataFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.fragment.PhotoGalleryFragment;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class CreatePlantActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PhotoGalleryFragment.PICK_IMAGE_CODE_CODE
                && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                //ImageView imageView = (ImageView) findViewById(R.id.imageView);
                //imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}
