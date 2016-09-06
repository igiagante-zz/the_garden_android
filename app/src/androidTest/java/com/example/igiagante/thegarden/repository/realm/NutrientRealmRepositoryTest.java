package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.realm.NutrientRealmRepository;
import com.example.igiagante.thegarden.repository.realm.utils.NutrientUtils;

import junit.framework.Assert;

import java.util.ArrayList;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class NutrientRealmRepositoryTest extends AndroidTestCase {

    private NutrientRealmRepository repository;
    private final String ID = "1";
    private final String NAME = "boost";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new NutrientRealmRepository(getContext());
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    public void testGetById() {
        // setup
        Nutrient nutrient = NutrientUtils.createNutrient(ID, NAME, 6, "5-3-4", "nice");
        repository.add(nutrient);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistNutrients() {

        // setup
        // create three nutrients
        ArrayList<Nutrient> nutrients = NutrientUtils.createNutrients();

        // when
        Observable<Integer> result = repository.add(nutrients);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testUpdateOneNutrientRealm() {

        // setup
        final String NEW_NAME = "booster";

        // setup
        Nutrient nutrient = NutrientUtils.createNutrient(ID, NAME, 6, "5-3-4", "nice");
        repository.add(nutrient);

        // when
        nutrient.setName(NEW_NAME);
        repository.update(nutrient);

        // assertions
        repository.getById(ID).subscribe(nutrientFromDB -> Assert.assertEquals(NEW_NAME, nutrientFromDB.getName()));
    }

    public void testPersistOneNutrientWithImages() {

        // setup
        // create nutrient with two images
        Nutrient nutrient = NutrientUtils.createNutrientWithImages();

        // when
        repository.add(nutrient);

        // assertions
        repository.getById(ID).subscribe(nutrientFromDB -> Assert.assertEquals(nutrientFromDB.getImages().size(), 2));
    }

    public void testRemoveOneNutrient() {

        // setup
        Nutrient nutrient = NutrientUtils.createNutrient(ID, NAME, 6, "5-3-4", "nice");
        repository.add(nutrient);

        repository.getById(ID).subscribe(nutrientFromDB -> Assert.assertEquals(nutrientFromDB.getName(), NAME));

        // when
        Observable<Integer> result = repository.remove(nutrient.getId());

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }




}
