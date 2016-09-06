package com.example.igiagante.thegarden.widgets;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.realm.mapper.IrrigationRealmToIrrigation;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.home.MainActivity;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

/**
 * @author Ignacio Giagante, on 6/9/16.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    public static String IRRIGATION_WIDGET_CLICK = "com.igiagante.widget.IRRIGATION_WIDGET_CLICK";

    /**
     * Called in response to the ACTION_APPWIDGET_UPDATE broadcast when this
     * AppWidget provider is being asked to provide RemoteViews for a set of
     * AppWidgets. Override this method to implement your own AppWidget
     * functionality.
     */
    public void onUpdate(Context context,
                         android.appwidget.AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        int appWidgetId = INVALID_APPWIDGET_ID;
        if (appWidgetIds != null) {
            int N = appWidgetIds.length;
            if (N == 1) {
                appWidgetId = appWidgetIds[0];
            }
        }

        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction("DO NOTHING ACTION");
        context.startService(intent);

    }

    /**
     * static class does not need instantiation UpdateWidgetService is a Service
     * that identifies the App Widgets , instantiates AppWidgetManager and calls
     * updateAppWidget() to update the widget values
     */
    public static class UpdateWidgetService extends IntentService {

        public UpdateWidgetService() {
            super("UpdateWidgetService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(this);

            int incomingAppWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID,  INVALID_APPWIDGET_ID);

            if (incomingAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                updateOneAppWidget(appWidgetManager, incomingAppWidgetId, getLastIrrigation());
            }
        }


        private Irrigation getLastIrrigation() {

            IrrigationRealmToIrrigation toIrrigation = new IrrigationRealmToIrrigation();

            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(UpdateWidgetService.this)
                    .name(Repository.DATABASE_NAME_DEV)
                    .deleteRealmIfMigrationNeeded()
                    .build();
             Realm realm = Realm.getInstance(realmConfiguration);

            Irrigation irrigation;

            RealmResults<IrrigationRealm> irrigationRealms = realm.where(IrrigationRealm.class).findAll();
            if(!irrigationRealms.isEmpty()) {
                irrigation = toIrrigation.map(irrigationRealms.get(irrigationRealms.size() - 1));
            } else {
                irrigation = new Irrigation();
            }

            return irrigation;
        }

        /**
         * For the random passcode app widget with the provided ID, updates its
         * display with a new passcode, and registers click handling for its
         * buttons.
         */
        private void updateOneAppWidget(AppWidgetManager appWidgetManager,
                                        int appWidgetId, Irrigation irrigation) {

            if(irrigation != null) {
                Log.d("Irrigation", String.valueOf(irrigation.getQuantity()));
            } else {
                Log.d("Irrigation", "IS NULL");
            }

            // initializing widget layout
            RemoteViews remoteViews = new RemoteViews(this.getPackageName(),
                    R.layout.widget_provider_layout);

            remoteViews.setOnClickPendingIntent(R.layout.widget_provider_layout, widget_last_irrigation(this));

            setupRemoteView(remoteViews, irrigation);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }

        private void setupRemoteView(RemoteViews remoteViews, Irrigation irrigation) {

            if(irrigation == null) {
                remoteViews.setEmptyView(R.layout.widget_provider_layout, R.id.no_irrigation_for_today);
            } else {
                // updating view with initial data
                remoteViews.setTextViewText(R.id.widget_dose_water_id, getString(R.string.dose_water, irrigation.getDose().getWater()));
                remoteViews.setTextViewText(R.id.widget_dose_ph_id, getString(R.string.dose_ph, irrigation.getDose().getPh()));
                remoteViews.setTextViewText(R.id.widget_dose_ec_id, getString(R.string.dose_ec, irrigation.getDose().getEc()));


                List<Nutrient> nutrients = irrigation.getDose().getNutrients();
                if(nutrients.isEmpty()) {
                    remoteViews.setTextViewText(R.id.widget_dose_list_of_nutrients, getString(R.string.dose_nutrients, ""));
                } else {
                    remoteViews.setTextViewText(R.id.widget_dose_list_of_nutrients,
                            getString(R.string.dose_nutrients, getNutrients(nutrients)));
                }

                String format = "dd MMM";
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format, Locale.US);

                remoteViews.setTextViewText(R.id.widget_card_view_irrigation_date_id,
                        getString(R.string.widget_irrigation_date, sdf.format(irrigation.getIrrigationDate())));
                remoteViews.setTextViewText(R.id.widget_card_view_irrigation_quantity_id,
                        getString(R.string.widget_irrigation_quantity, irrigation.getQuantity()));
            }
        }

        private String getNutrients(List<Nutrient> nutrients) {

            StringBuilder nutrientsText = new StringBuilder();

            for (int i = 0; i < nutrients.size(); i++) {

                nutrientsText.append(nutrients.get(i).getName());

                if(i != nutrients.size() - 1) {
                    nutrientsText.append(" - ");
                }
            }
            return nutrientsText.toString();
        }

        public PendingIntent widget_last_irrigation(Context context) {
            // initiate widget update request
            Intent intent = new Intent(context, MainActivity.class);
            intent.setAction(IRRIGATION_WIDGET_CLICK);
            return PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

    }
}