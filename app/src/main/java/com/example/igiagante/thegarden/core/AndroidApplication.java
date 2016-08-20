package com.example.igiagante.thegarden.core;

import android.app.Application;
import android.util.Log;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.components.DaggerApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ApplicationModule;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.home.plants.usecase.PersistStaticDataUseCase;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private static final String TAG = AndroidApplication.class.getSimpleName();

    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-82877624-1";

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    private ApplicationComponent applicationComponent;

    @Inject
    PersistStaticDataUseCase persistStaticDataUseCase;

    public enum TrackerName {
        APP_TRACKER,
        GLOBAL_TRACKER,
        E_COMMERCE_TRACKER,
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID) : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker) : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        RxPaparazzo.register(this);
        Fresco.initialize(this);

        applicationComponent.inject(this);

        // update database
        persistStaticDataUseCase.execute(null, new ApplicationSubscriber());
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

private final class ApplicationSubscriber extends DefaultSubscriber<String> {

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        Log.e("Error", e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void onNext(String result) {
        Log.i(TAG, "The database's update was " + result);
    }
}
}
