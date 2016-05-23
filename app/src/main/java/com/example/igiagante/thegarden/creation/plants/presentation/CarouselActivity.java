package com.example.igiagante.thegarden.creation.plants.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.core.ui.CirclePageIndicator;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.CarouselAdapter;

/**
 * @author Ignacio Giagante, on 23/5/16.
 */
public class CarouselActivity extends BaseActivity {

    public static final String PICTURE_SELECTED_KEY = "PICTURE_SELECTED";
    public static final String PICTURE_URLS_KEY = "PICTURE_URLS";

    private CirclePageIndicator mIndicator;
    private ViewPager mPager;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_activity);

        Intent intent = getIntent();
        int position = intent.getIntExtra(PICTURE_SELECTED_KEY, 0);
        String [] urls = intent.getStringArrayExtra(PICTURE_URLS_KEY);

        mPager = (ViewPager) findViewById(R.id.viewpager_carousel);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        CarouselAdapter adapter = new CarouselAdapter(getSupportFragmentManager());
        adapter.setUrlsImages(urls);
        mPager.setAdapter(adapter);
        mIndicator.setViewPager(mPager, position);
    }
}
