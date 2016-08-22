package com.example.igiagante.thegarden.home.charts.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.igiagante.thegarden.core.repository.realm.SensorTempRealmRepository;

/**
 * @author Ignacio Giagante, on 22/8/16.
 */
public class ChartsDataService extends IntentService {

    //Params
    public static final String PARAM_WEATHER_DATA_KEY = "PARAM_WEATHER_DATA";

    //Actions
    public static final String ACTION_CLEAN_DATABASE = "ACTION_CLEAN_DATABASE";

    //Notifications
    public static final String NOTIFICATION_CLEAN_DATABASE = "NOTIFICATION_CLEAN_DATABASE";

    public ChartsDataService() {
        super("ChartsDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_CLEAN_DATABASE.equals(action)) {
                cleanDataBase();
            }
        }
    }

    private void cleanDataBase() {
        SensorTempRealmRepository repository = new SensorTempRealmRepository(this);
        repository.removeAll();

        Intent intent = new Intent();
        intent.setAction(NOTIFICATION_CLEAN_DATABASE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
