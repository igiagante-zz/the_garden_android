package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.realm.DoseRealmRepository;

import junit.framework.Assert;

import java.util.ArrayList;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseRealmRepositoryTest extends AndroidTestCase {

    private DoseRealmRepository repository;
    private final String ID = "1";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new DoseRealmRepository(getContext());
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    public void testGetById() {
        // setup
        Dose dose = createDose(ID, 3, 1, 6, (float)1.0, true);
        repository.add(dose);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getWater(), (float)3)
        );
    }

    public void testPersistDoses() {

        // setup
        // create three doses
        ArrayList<Dose> doses = createDoses();

        // when
        Observable<Integer> result = repository.add(doses);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    private ArrayList<Dose> createDoses() {

        ArrayList<Dose> doses = new ArrayList<>();

        Dose doseOne = createDose("1", 1, 3, 4, (float)0.8, true);
        Dose doseTwo = createDose("2", 1, 5, 6, (float)0.6, true);
        Dose doseThree = createDose("3", 1, 2, 3, (float)0.9, true);

        doses.add(doseOne);
        doses.add(doseTwo);
        doses.add(doseThree);

        return doses;
    }

    private Dose createDose(String id, float water, float phDose, float ph, float ec, boolean editable) {

        Dose dose = new Dose();
        dose.setId(id);
        dose.setWater(water);
        dose.setPhDose(phDose);
        dose.setPh(ph);
        dose.setEc(ec);

        ArrayList<Nutrient> nutrients = new ArrayList<>();

        Nutrient nutrientOne = createNutrient("1", "root", 6, "1-1-1", "nice");
        Nutrient nutrientTwo = createNutrient("2", "veg", 6, "5-2-3", "abius");
        Nutrient nutrientThree = createNutrient("3", "flora", (float)6.5, "1-20-17", "right");

        nutrients.add(nutrientOne);
        nutrients.add(nutrientTwo);
        nutrients.add(nutrientThree);

        dose.setNutrients(nutrients);

        return dose;
    }

    private Nutrient createNutrient(String id, String name, float ph, String npk, String description) {

        Nutrient nutrient = new Nutrient();
        nutrient.setId(id);
        nutrient.setName(name);
        nutrient.setPh(ph);
        nutrient.setNpk(npk);
        nutrient.setDescription(description);

        return nutrient;
    }
}
