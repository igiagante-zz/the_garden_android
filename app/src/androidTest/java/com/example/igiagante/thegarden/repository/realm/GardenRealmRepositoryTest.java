package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.garden.GardenSpecification;
import com.example.igiagante.thegarden.repository.realm.utils.GardenUtils;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class GardenRealmRepositoryTest extends AndroidTestCase {

    private GardenRealmRepository repository;
    private final String ID = "1";
    private final String NAME = "la selva";
    private final Date DATE = new Date();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new GardenRealmRepository(getContext());
        repository.removeAll();

        Calendar c = Calendar.getInstance();
        c.setTime(DATE); // Now use today date.
        c.add(Calendar.MONTH, 2); // Adding 5 days
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    public void testGetById() {
        // setup
        Garden garden = GardenUtils.createGarden(ID, NAME, new Date(), DATE);
        repository.add(garden);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testGetAll() {

        // setup
        Garden garden = GardenUtils.createGardenWithPlants();

        // when
        repository.add(garden);
        Observable<List<Garden>> query = repository.query(new GardenSpecification());

        // verify
        query.subscribe(
                item -> Assert.assertEquals(item.get(0).getPlants().size(), 2)
        );
    }

    public void testPersistGardens() {

        // setup
        // create three gardens
        ArrayList<Garden> gardens = GardenUtils.createGardens();

        // when
        Observable<Integer> result = repository.add(gardens);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testPersistOneGardenWithPlants() {

        // setup
        Garden garden = GardenUtils.createGardenWithPlants();

        // when
        Observable<Garden> result = repository.add(garden);

        // assertions
        result.subscribe(garden1 -> Assert.assertEquals(2, garden1.getPlants().size()));
    }

    public void testUpdateOneGardenRealm() {

        // setup
        final String NEW_NAME = "el mundo";

        Garden garden = GardenUtils.createGarden(ID, NAME, new Date(), DATE);
        repository.add(garden);

        // when
        garden.setName(NEW_NAME);
        repository.update(garden);

        // assertions
        repository.getById(ID).subscribe(gardenFromDB -> Assert.assertEquals(NEW_NAME, gardenFromDB.getName()));
    }



}
