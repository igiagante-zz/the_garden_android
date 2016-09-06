package com.example.igiagante.thegarden.widgets;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.repository.realm.IrrigationRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.irrigations.IrrigationSpecification;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 6/9/16.
 */
public class WidgetService extends IntentService {

    public static final String PARAM_IRRIGATION = "PARAM_IRRIGATION";

    public static final String ACTION_GET_LAST_IRRIGATION = "ACTION_GET_LAST_IRRIGATION";

    public static final String NOTIFICATION_GET_LAST_IRRIGATION = "NOTIFICATION_GET_LAST_IRRIGATION";

    public WidgetService() {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.getAction().equals(ACTION_GET_LAST_IRRIGATION)) {
            getLastIrrigation();
        }
    }

    private void getLastIrrigation() {

        IrrigationRealmRepository irrigationRealmRepository = new IrrigationRealmRepository(this);
        Observable<List<Irrigation>> resultFromDB = irrigationRealmRepository.query(new IrrigationSpecification());

        List<Irrigation> list = new ArrayList<>();
        resultFromDB.subscribeOn(Schedulers.io()).toBlocking().subscribe(irrigation -> list.addAll(irrigation));

        if (!list.isEmpty()) {
            publishResults(list.get(0));
        } else {
            publishResults(new Irrigation());
        }
    }

    private void publishResults(Irrigation irrigation) {
        Intent intent = new Intent();
        intent.putExtra(PARAM_IRRIGATION, irrigation);
        intent.setAction(NOTIFICATION_GET_LAST_IRRIGATION);
        sendBroadcast(intent);
    }
}
