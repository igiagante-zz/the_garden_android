package com.example.igiagante.thegarden.home.plants.usecase;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.executor.PostExecutionThread;
import com.example.igiagante.thegarden.core.executor.ThreadExecutor;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.PlagueRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.SensorTempRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.SensorTempSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.attribute.AttributeSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.plague.PlagueSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiAttributeRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiFlavorRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiPlagueRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiSensorTempRepository;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDao;
import com.example.igiagante.thegarden.core.usecase.UseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 15/6/16.
 *         <p>
 *         This usecase is used to retrieve data that almost never change. Attributes, Flavors and Plagues
 */
@Singleton
public class PersistStaticDataUseCase extends UseCase<Void> {

    private static final String TAG = PersistStaticDataUseCase.class.getSimpleName();

    /**
     * Api repositories
     */
    private final RestApiAttributeRepository apiAttributeRepository;
    private final RestApiFlavorRepository apiFlavorRepository;
    private final RestApiPlagueRepository apiPlagueRepository;
    private final RestApiSensorTempRepository restApiSensorTempRepository;

    /**
     * Database repositories
     */
    private final AttributeRealmRepository attributeRealmRepository;
    private final FlavorDao flavorDao;
    private final PlagueRealmRepository plagueRealmRepository;
    private final SensorTempRealmRepository sensorTempRealmRepository;

    private Context context;

    @Inject
    public PersistStaticDataUseCase(Context context, Session session, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.context = context;

        this.apiAttributeRepository = new RestApiAttributeRepository();
        this.apiFlavorRepository = new RestApiFlavorRepository();
        this.apiPlagueRepository = new RestApiPlagueRepository();
        this.restApiSensorTempRepository = new RestApiSensorTempRepository();

        this.attributeRealmRepository = new AttributeRealmRepository(context);
        this.flavorDao = new FlavorDao(context);
        this.plagueRealmRepository = new PlagueRealmRepository(context);
        this.sensorTempRealmRepository = new SensorTempRealmRepository(context);
    }

    @Override
    protected Observable buildUseCaseObservable(Void aVoid) {

        // ask if attributes are already persisted
        AttributeSpecification attributeSpecification = new AttributeSpecification();

        Observable<List<Attribute>> attributesObservable = attributeRealmRepository.query(attributeSpecification);

        ArrayList<Attribute> attributesFromDB = new ArrayList<>();
        attributesObservable.subscribe(list -> attributesFromDB.addAll(list));

        // if there are any attribute in DB, let's ask to the api
        if (attributesFromDB.isEmpty()) {

            ArrayList<Attribute> attributesFromApi = new ArrayList<>();

            apiAttributeRepository.query(attributeSpecification)
                    .subscribeOn(Schedulers.io())
                    .toBlocking()
                    .subscribe(list -> attributesFromApi.addAll(list),
                            error -> Log.d(TAG, error.toString()));

            Observable<Integer> rows = attributeRealmRepository.add(attributesFromApi);
            rows.toBlocking().subscribe(row -> Log.i(TAG, "  ROWS " + row + " attributes were inserted into Realm DB"));
        }

        // ask if flavors are already persisted (Sqlite)
        List<Flavor> flavorsObservable = flavorDao.getFlavors();

        // if there are any flavor in DB, let's ask to the api
        if (flavorsObservable.isEmpty()) {

            apiFlavorRepository.query(new Specification() {
            })
                    .subscribeOn(Schedulers.io())
                    .toBlocking()
                    .subscribe(list -> {
                                int rows = flavorDao.add(list);
                                Log.i(TAG, "   ROWS:  " + rows + "   flavors were inserted into Realm DB");
                            },
                            error -> Log.d(TAG, error.toString()));
        }

        // ask if plagues are already persisted
        PlagueSpecification plagueSpecification = new PlagueSpecification();

        Observable<List<Plague>> plaguesObservable = plagueRealmRepository.query(plagueSpecification);

        ArrayList<Plague> plaguesFromDB = new ArrayList<>();
        plaguesObservable.subscribe(list -> plaguesFromDB.addAll(list));

        // if there are any plague in DB, let's ask to the api
        if (plaguesFromDB.isEmpty()) {

            ArrayList<Plague> plaguesFromApi = new ArrayList<>();

            apiPlagueRepository.query(plagueSpecification)
                    .subscribeOn(Schedulers.io())
                    .toBlocking()
                    .subscribe(list -> plaguesFromApi.addAll(list),
                            error -> Log.d(TAG, error.toString()));

            Observable<Integer> rows = plagueRealmRepository.add(plaguesFromApi);
            rows.toBlocking().subscribe(row -> Log.i(TAG, "   ROWS:  " + row + "   plagues were inserted into Realm DB"));
        }

        // check temp and humidity data
        SensorTempSpecification sensorTempSpecification = new SensorTempSpecification();
        Observable<List<SensorTemp>> sensorData = sensorTempRealmRepository.query(sensorTempSpecification);

        ArrayList<SensorTemp> sensorDataFromDB = new ArrayList<>();
        sensorData.subscribe(list -> sensorDataFromDB.addAll(list));

        if (sensorDataFromDB.isEmpty()) {
            Observable<List<SensorTemp>> apiResult = restApiSensorTempRepository.query(null);

            // get Data From api
            ArrayList<SensorTemp> sensorTemps = new ArrayList<>();
            apiResult.subscribeOn(Schedulers.io()).toBlocking().subscribe(data -> sensorTemps.addAll(data));

            SensorTempRealmRepository repository = new SensorTempRealmRepository(context);
            Observable<Integer> rows = repository.add(sensorTemps);
            rows.toBlocking().subscribe(row -> Log.i(TAG, "   ROWS:  " + row + "  temps were inserted into Realm DB"));
        }

        return Observable.just("OK");
    }
}
