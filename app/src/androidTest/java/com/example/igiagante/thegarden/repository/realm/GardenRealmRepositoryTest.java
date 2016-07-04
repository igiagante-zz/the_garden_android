package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        Garden garden = createGarden(ID, NAME, new Date(), DATE);
        repository.add(garden);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistGardens() {

        // setup
        // create three gardens
        ArrayList<Garden> gardens = createGardens();

        // when
        Observable<Integer> result = repository.add(gardens);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testUpdateOnePlantRealm() {

        // setup
        final String NEW_NAME = "el mundo";

        Garden garden = createGarden(ID, NAME, new Date(), DATE);
        repository.add(garden);

        // when
        garden.setName(NEW_NAME);
        repository.update(garden);

        // assertions
        repository.getById(ID).subscribe(gardenFromDB -> Assert.assertEquals(NEW_NAME, gardenFromDB.getName()));
    }

    private Garden createGarden(String id, String name, Date startDate, Date endDate) {

        Garden garden = new Garden();
        garden.setId(id);
        garden.setName(name);
        garden.setStartDate(startDate);
        garden.setEndDate(endDate);

        return garden;
    }

    private ArrayList<Garden> createGardens() {

        ArrayList<Garden> gardens = new ArrayList<>();

        Garden gardenOne = createGarden("1", "la selva", new Date(), DATE);
        Garden gardenTwo = createGarden("2", "yunga", new Date(), DATE);
        Garden gardenThree = createGarden("3", "la casita", new Date(), DATE);

        gardens.add(gardenOne);
        gardens.add(gardenTwo);
        gardens.add(gardenThree);

        return gardens;
    }
}
