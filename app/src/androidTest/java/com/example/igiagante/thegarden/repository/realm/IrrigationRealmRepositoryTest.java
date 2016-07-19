package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.IrrigationRealmRepository;
import com.example.igiagante.thegarden.repository.realm.utils.IrrigationUtils;
import com.example.igiagante.thegarden.repository.realm.utils.PlantUtils;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Date;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class IrrigationRealmRepositoryTest extends AndroidTestCase {

    private IrrigationRealmRepository repository;
    private final String ID = "1";
    private final Date DATE = new Date();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new IrrigationRealmRepository(getContext());
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    public void testGetById() {
        // setup
        Irrigation irrigation = IrrigationUtils.createIrrigation(ID);
        repository.add(irrigation);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getId(), irrigation.getId())
        );
    }

    public void testPersistIrrigations() {

        ArrayList<Irrigation> irrigations = IrrigationUtils.createIrrigations();

        // when
        Observable<Integer> result = repository.add(irrigations);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testUpdateOnePlantRealm() {

        // setup
        final float QUANTITY = 5;

        Irrigation irrigation = IrrigationUtils.createIrrigation(ID);
        repository.add(irrigation);

        // when
        irrigation.setQuantity(QUANTITY);
        repository.update(irrigation);

        // assertions
        repository.getById(ID).subscribe(irrigationFromDB ->
                Assert.assertEquals(QUANTITY, irrigationFromDB.getQuantity()));
    }

    public void testRemoveOneIrrigation() {

        Irrigation irrigation = IrrigationUtils.createIrrigation(ID);
        repository.add(irrigation);

        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getId(), irrigation.getId()));

        // when
        Observable<Integer> result = repository.remove(irrigation.getId());

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }
}
