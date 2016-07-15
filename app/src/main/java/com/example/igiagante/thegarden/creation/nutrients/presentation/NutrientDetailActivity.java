package com.example.igiagante.thegarden.creation.nutrients.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.nutrients.di.DaggerNutrientsComponent;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientsComponent;
import com.example.igiagante.thegarden.creation.nutrients.presentation.fragments.NutrientDetailFragment;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientDetailView;
import com.example.igiagante.thegarden.creation.plants.presentation.fragments.PhotoGalleryFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientDetailActivity extends BaseActivity implements HasComponent<NutrientsComponent> {

    public static final String NUTRIENT_KEY = "NUTRIENT";
    private static final String FRAGMENT_NUTRIENT_DATA_TAG = "FRAGMENT_NUTRIENT_DATA";
    private static final String FRAGMENT_NUTRIENT_IMAGES_TAG = "FRAGMENT_NUTRIENT_IMAGES_KEY";

    private NutrientsComponent nutrientsComponent;

    private Nutrient mNutrient;

    @Bind(R.id.nutrient_save_button)
    Button mSaveButton;

    @Bind(R.id.nutrient_cancel_button)
    Button mCancelButton;

    @Bind(R.id.nutrient_detail_progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();

        setContentView(R.layout.nutrient_detail_activity);
        ButterKnife.bind(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.nutrient_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String title = getString(R.string.nutrient_detail_title);
        ((TextView) findViewById(R.id.nutrient_detail_toolbar_title)).setText(title);

        mNutrient = getIntent().getParcelableExtra(NUTRIENT_KEY);

        Fragment galleryFragment;
        Fragment dataFragment;

        if(mNutrient != null) {
            dataFragment = NutrientDetailFragment.newInstance(mNutrient);
            galleryFragment = PhotoGalleryFragment.newInstance((ArrayList<Image>) mNutrient.getImages());
        } else {
            dataFragment = new NutrientDetailFragment();
            galleryFragment = new PhotoGalleryFragment();
        }

        addFragment(R.id.nutrient_container_data, dataFragment, FRAGMENT_NUTRIENT_DATA_TAG);
        addFragment(R.id.nutrient_container_photo_gallery, galleryFragment, FRAGMENT_NUTRIENT_IMAGES_TAG);

        mSaveButton.setOnClickListener(v -> {
            mProgressBar.setVisibility(View.VISIBLE);
            saveNutrient();
        });

        mCancelButton.setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }

    private void saveNutrient() {
        NutrientDetailFragment nutrientDetailFragment = (NutrientDetailFragment) getFragmentByTag(FRAGMENT_NUTRIENT_DATA_TAG);
        PhotoGalleryFragment photoGalleryFragment = (PhotoGalleryFragment) getFragmentByTag(FRAGMENT_NUTRIENT_IMAGES_TAG);
        this.mNutrient = nutrientDetailFragment.getNutrient();
        if(mNutrient != null) {
            mNutrient.setImages(photoGalleryFragment.getImages());
            mNutrient.setResourcesIds(photoGalleryFragment.getResourcesIds());
        }
        persistNutrient(mNutrient);
    }

    @Override
    public NutrientsComponent getComponent() {
        return this.nutrientsComponent;
    }

    private void initializeInjector() {
        this.nutrientsComponent = DaggerNutrientsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    private void persistNutrient(Nutrient nutrient) {
        Intent intent = new Intent();
        intent.putExtra(NUTRIENT_KEY, nutrient);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}