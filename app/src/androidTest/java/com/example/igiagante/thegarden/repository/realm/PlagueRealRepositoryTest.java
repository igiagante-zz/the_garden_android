package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.realm.PlagueRealmRepository;

import junit.framework.Assert;

import java.util.ArrayList;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 6/6/16.
 */
public class PlagueRealRepositoryTest extends AndroidTestCase {

    private PlagueRealmRepository repository;
    private final String ID = "1";
    private final String NAME = "caterpillar";
    private final String IMAGE_URL = "/image/plagues/caterpillar.jpg";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new PlagueRealmRepository(getContext());
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    public void testGetById() {
        // setup
        Plague plague = createPlague(ID, NAME, IMAGE_URL);
        repository.add(plague);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistOnePlague() {

        // setup
        Plague plague = createPlague(ID, NAME, IMAGE_URL);

        // when
        repository.add(plague);

        // assert
        repository.getById(ID).subscribe(plagueFromDB -> Assert.assertEquals(plagueFromDB.getName(), NAME));
    }

    /**
     * Test add(final Iterable<Plague> plagues)
     */
    public void testPersistPlagues() {

        // setup
        // create three plagues
        ArrayList<Plague> plagues = createPlagues();

        // when
        Observable<Integer> result = repository.add(plagues);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testRemoveOnePlague() {

        // setup
        Plague plague = createPlague(ID, NAME, IMAGE_URL);

        repository.add(plague);

        repository.getById(ID).subscribe(plagueFromDB -> Assert.assertEquals(plagueFromDB.getName(), NAME));

        // when
        Observable<Integer> result = repository.remove(plague.getId());

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }

    private Plague createPlague(String id, String name, String imageUrl) {

        Plague plague = new Plague();

        plague.setId(id);
        plague.setName(name);
        plague.setImageUrl(imageUrl);

        return plague;
    }

    /**
     * Create a list of plagues
     *
     * @return plagues
     */
    private ArrayList<Plague> createPlagues() {

        ArrayList<Plague> plagues = new ArrayList<>();

        Plague plagueOne = createPlague(ID, NAME, IMAGE_URL);
        Plague plagueTwo = createPlague("2", "trip", "/image/plagues/trip.jpg");
        Plague plagueThree = createPlague("3", "spider", "/image/plagues/spider.jpg");

        plagues.add(plagueOne);
        plagues.add(plagueTwo);
        plagues.add(plagueThree);

        return plagues;
    }
}