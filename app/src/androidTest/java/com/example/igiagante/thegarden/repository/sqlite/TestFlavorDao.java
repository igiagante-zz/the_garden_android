package com.example.igiagante.thegarden.repository.sqlite;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.sqlite.FlavorDao;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 30/5/16.
 */
public class TestFlavorDao extends AndroidTestCase {

    FlavorDao flavorDao;

    public void deleteAllRecords() {
        TestUtilities.cleanDataBase(mContext);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
        flavorDao = new FlavorDao(mContext);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        deleteAllRecords();
    }

    public void testAddFlavor(){

        Flavor flavor = TestUtilities.buildFlavor("123");

        long flavorId = flavorDao.createFlavor(flavor);

        Flavor flavorFromDb = flavorDao.getFlavor(String.valueOf(flavorId));

        assertEquals(flavor.getName(), flavorFromDb.getName());
        assertEquals(flavor.getImageUrl(), flavorFromDb.getImageUrl());
        assertEquals(flavor.getMongoId(), flavorFromDb.getMongoId());
    }

    public void testGetAllFlavors(){

        Flavor flavorOne = TestUtilities.buildFlavor("1");

        flavorDao.createFlavor(flavorOne);

        Flavor flavorTwo = TestUtilities.buildFlavor("2");

        flavorDao.createFlavor(flavorTwo);

        Flavor flavorThree = TestUtilities.buildFlavor("3");

        flavorDao.createFlavor(flavorThree);

        ArrayList<Flavor> flavors = flavorDao.getFlavors();

        assertEquals(flavors.size(), 3);
    }

    public void testBulkInsert() {

        ArrayList<Flavor> flavors = createFlavorsValues();

        int rows = flavorDao.add(flavors);

        assertEquals(rows, 3);

        Flavor flavor = flavors.get(0);
        Flavor flavorFromDb = flavorDao.getFlavor(String.valueOf(flavor.getId()));

        assertEquals(flavor.getId(), flavorFromDb.getId());
        assertEquals(flavor.getName(), flavorFromDb.getName());
        assertEquals(flavor.getImageUrl(), flavorFromDb.getImageUrl());

        ArrayList<Flavor> flavorsList = flavorDao.getFlavors();

        assertEquals(flavorsList.size(), 3);
    }

    private ArrayList<Flavor> createFlavorsValues() {

        ArrayList<Flavor> flavors = new ArrayList<>();

        Flavor one = createOneFlavor("1", "lemon", "/images/lemon.jpg", "57605cf782a9a92ee597f7c7");
        Flavor two = createOneFlavor("2", "skunk", "/images/skunk.jpg", "57605cf782a9a92ee597f7c8");
        Flavor three = createOneFlavor("3", "wood", "/images/wood.jpg", "57605cf782a9a92ee597f7c9");

        flavors.add(one);
        flavors.add(two);
        flavors.add(three);

        return  flavors;
    }

    private Flavor createOneFlavor(String id, String name, String imageUrl, String mongoId) {
        Flavor flavor = new Flavor();
        flavor.setId(id);
        flavor.setName(name);
        flavor.setImageUrl(imageUrl);
        flavor.setMongoId(mongoId);
        return flavor;
    }
}