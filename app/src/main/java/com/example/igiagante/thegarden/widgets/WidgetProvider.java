package com.example.igiagante.thegarden.widgets;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.realm.mapper.IrrigationRealmToIrrigation;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.IrrigationRealm;
import com.example.igiagante.thegarden.home.MainActivity;

import java.text.DecimalFormat;
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
public class WidgetProvider extends AppWidgetProvider {

    public static String IRRIGATION_WIDGET_CLICK = "com.igiagante.widget.action.IRRIGATION_WIDGET_CLICK";
    public static String IRRIGATION_WIDGET_UPDATE = "com.igiagante.widget.action.IRRIGATION_WIDGET_UPDATE";

    /**
     * Called in response to the ACTION_APPWIDGET_UPDATE broadcast when this
     * AppWidget provider is being asked to provide RemoteViews for a set of
     * AppWidgets. Override this method to implement your own AppWidget
     * functionality.
     */
    @Override
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

        createIntentForUpdate(context, appWidgetId);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (IRRIGATION_WIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));

            for (int appWidgetId : appWidgetIds) {
                createIntentForUpdate(context, appWidgetId);
            }
        }
    }

    private void createIntentForUpdate(Context context, int appWidgetId) {
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

            int incomingAppWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);

            if (incomingAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                updateOneAppWidget(appWidgetManager, incomingAppWidgetId, getLastIrrigation());
            }
        }

        public Irrigation getLastIrrigation() {

            IrrigationRealmToIrrigation toIrrigation = new IrrigationRealmToIrrigation();

            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(UpdateWidgetService.this)
                    .name(Repository.DATABASE_NAME_DEV)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm realm = Realm.getInstance(realmConfiguration);

            Irrigation irrigation;

            RealmResults<IrrigationRealm> irrigationRealms = realm.where(IrrigationRealm.class).findAll();
            if (!irrigationRealms.isEmpty()) {
                irrigation = toIrrigation.map(irrigationRealms.get(irrigationRealms.size() - 1));
            } else {
                irrigation = new Irrigation();
            }

            return irrigation;
        }

        public void updateOneAppWidget(AppWidgetManager appWidgetManager,
                                       int appWidgetId, Irrigation irrigation) {

            // initializing widget layout
            RemoteViews remoteViews = new RemoteViews(this.getPackageName(),
                    R.layout.widget_provider_layout);

            remoteViews.setOnClickPendingIntent(R.id.widget_last_irrigation, widget_last_irrigation(this));

            setupRemoteView(remoteViews, irrigation);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }

        public void setupRemoteView(RemoteViews remoteViews, Irrigation irrigation) {

            if (irrigation == null) {
                remoteViews.setEmptyView(R.layout.widget_provider_layout, R.id.no_irrigation_for_today);
            } else {
                // updating view with initial data
                remoteViews.setTextViewText(R.id.widget_dose_water_id, getString(R.string.dose_water, irrigation.getDose().getWater()));
                remoteViews.setTextViewText(R.id.widget_dose_ph_id, getString(R.string.dose_ph, irrigation.getDose().getPh()));
                remoteViews.setTextViewText(R.id.widget_dose_ec_id, getString(R.string.dose_ec, irrigation.getDose().getEc()));


                List<Nutrient> nutrients = irrigation.getDose().getNutrients();
                if (nutrients.isEmpty()) {
                    remoteViews.setTextViewText(R.id.widget_dose_list_of_nutrients, getString(R.string.dose_nutrients, ""));
                } else {
                    remoteViews.setTextViewText(R.id.widget_dose_list_of_nutrients,
                            getString(R.string.dose_nutrients, getNutrients(nutrients)));
                }

                String format = "dd MMM";
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format, Locale.getDefault());

                remoteViews.setTextViewText(R.id.widget_card_view_irrigation_date_id,
                        getString(R.string.widget_irrigation_date, sdf.format(irrigation.getIrrigationDate())));
                remoteViews.setTextViewText(R.id.widget_card_view_irrigation_quantity_id,
                        getString(R.string.widget_irrigation_quantity, irrigation.getQuantity()));
            }
        }

        @NonNull
        public String getNutrients(List<Nutrient> nutrients) {

            StringBuilder nutrientsText = new StringBuilder();

            for (int i = 0; i < nutrients.size(); i++) {

                nutrientsText.append(nutrients.get(i).getName());
                nutrientsText.append(" ");

                float quantityUsed = nutrients.get(i).getQuantityUsed();
                DecimalFormat formatter = new DecimalFormat("#");
                String quantity = formatter.format(quantityUsed);
                nutrientsText.append(quantity);
                nutrientsText.append("mL");

                if (i != nutrients.size() - 1) {
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