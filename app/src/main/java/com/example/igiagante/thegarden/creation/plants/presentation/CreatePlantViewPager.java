package com.example.igiagante.thegarden.creation.plants.presentation;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Ignacio Giagante, on 9/4/16.
 */

public class CreatePlantViewPager extends ViewPager {

    private boolean pagingEnabled = true;

    public CreatePlantViewPager(Context context) {
        super(context);
    }

    public CreatePlantViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPagingEnabled(boolean enabled) {
        pagingEnabled = enabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!pagingEnabled) {
            return false; // do not intercept
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!pagingEnabled) {
            return false; // do not consume
        }
        return super.onTouchEvent(event);
    }
}
