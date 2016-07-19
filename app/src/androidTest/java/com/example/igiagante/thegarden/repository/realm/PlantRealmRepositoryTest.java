package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.PlagueRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.plant.PlantByNameSpecification;
import com.example.igiagante.thegarden.core.repository.realm.specification.plant.PlantSpecification;
import com.example.igiagante.thegarden.repository.realm.utils.PlantUtils;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Date;

import rx.Observable;

/**
 * @author Ignacio Giagante, on 28/4/16.
 */
public class PlantRealmRepositoryTest extends AndroidTestCase {

    private PlantRealmRepository plantRealmRepository;
    private AttributeRealmRepository attributeRealmRepository;
    private PlagueRealmRepository plagueRealmRepository;

    private final String ID = "1";
    private final String NAME = "mango";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        plantRealmRepository = new PlantRealmRepository(getContext());
        attributeRealmRepository = new AttributeRealmRepository(getContext());
        plagueRealmRepository = new PlagueRealmRepository(getContext());
        plantRealmRepository.removeAll();
        loadFirstData();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        plantRealmRepository.removeAll();
    }

    private void loadFirstData() {
        attributeRealmRepository.add(PlantUtils.createAttributes());
        plagueRealmRepository.add(PlantUtils.createPlagues());

        // verify
        attributeRealmRepository.getById("1").subscribe(
                item -> Assert.assertEquals(item.getName(), "Euphoric")
        );

        // verify
        plagueRealmRepository.getById("1").subscribe(
                item -> Assert.assertEquals(item.getName(), "caterpillar")
        );
    }

    public void testGetById() {

        // setup
        Plant plant = PlantUtils.createPlant(ID, NAME);
        plantRealmRepository.add(plant);

        // verify
        plantRealmRepository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistOnePlant() {

        Plant plant = PlantUtils.createPlant(ID, NAME);

        plantRealmRepository.add(plant);

        plantRealmRepository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getName(), NAME));
    }

    /**
     * Test add(final Iterable<Plant> plants)
     */
    public void testPersistPlants() {

        // setup
        // create three plants
        ArrayList<Plant> plants = PlantUtils.createPlants();

        // when
        Observable<Integer> result = plantRealmRepository.add(plants);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testUpdateOnePlantRealm() {

        // setup
        final String NEW_NAME = "mango2";

        Plant plant = PlantUtils.createPlant(ID, NAME);

        plantRealmRepository.add(plant);

        // when
        plant.setName(NEW_NAME);
        plantRealmRepository.update(plant);

        // assertions
        plantRealmRepository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(NEW_NAME, plantFromDB.getName()));
    }

    public void testPersistOnePlantWithImages() {

        // setup
        // create plant with two images
        Plant plant = PlantUtils.createPlantWithImages(ID, NAME);

        // when
        plantRealmRepository.add(plant);

        // assertions
        plantRealmRepository.getById(ID).subscribe(attributeFromDB -> Assert.assertEquals(attributeFromDB.getImages().size(), 2));
    }

    public void testPersistPlantsWithImages() {

        // setup
        // create two plants with two images each one
        ArrayList<Plant> plants = PlantUtils.createPlantsWithImages();

        // when
        Observable<Integer> result = plantRealmRepository.add(plants);

        // assertions
        result.subscribe(count -> Assert.assertEquals(2, count.intValue()));

        plantRealmRepository.query(new PlantSpecification()).subscribe(
                attributeFromDB -> Assert.assertEquals(2, attributeFromDB.get(0).getImages().size())
        );
    }

    public void testPersistPlantWithAll() {
        // setup
        // create plant with two images
        Plant plant = PlantUtils.createPlantWithAll(ID, NAME);

        // when
        plantRealmRepository.add(plant);

        // assertions
        plantRealmRepository.getById(ID).subscribe(plantFromDB -> {
            Assert.assertEquals(plantFromDB.getImages().size(), 2);
            Assert.assertEquals(plantFromDB.getFlavors().size(), 3);
            Assert.assertEquals(plantFromDB.getAttributes().size(), 3);
            Assert.assertEquals(plantFromDB.getAttributes().get(0).getPercentage(), 30);
            Assert.assertEquals(plantFromDB.getAttributes().get(1).getPercentage(), 50);
            Assert.assertEquals(plantFromDB.getPlagues().size(), 4);
        });
    }

    public void testPersistPlantWithAllForUpdate() {
        // setup
        // create plant with two images
        Plant plant = PlantUtils.createPlantWithAll(ID, NAME);

        Plant plantUpdated = PlantUtils.createPlantWithAllForUpdate(ID, NAME);

        // when
        plantRealmRepository.add(plant);

        plantRealmRepository.update(plantUpdated);

        // assertions
        plantRealmRepository.getById(ID).subscribe(plantFromDB -> {
            Assert.assertEquals(plantFromDB.getName(), NAME);
            Assert.assertNotNull(plantFromDB.getImages().get(0));
            Assert.assertNotNull(plantFromDB.getImages().get(1));
            Assert.assertEquals(plantFromDB.getFlavors().size(), 2);
            Assert.assertEquals(plantFromDB.getFlavors().get(1).getName(), "soil");
            Assert.assertEquals(plantFromDB.getAttributes().size(), 2);
            Assert.assertEquals(plantFromDB.getAttributes().get(0).getPercentage(), 50);
            Assert.assertEquals(plantFromDB.getAttributes().get(1).getPercentage(), 60);
            Assert.assertEquals(plantFromDB.getPlagues().size(), 3);
        });
    }

    public void testRemoveOnePlant() {

        // setup
        Plant plant = PlantUtils.createPlant(ID, NAME);

        plantRealmRepository.add(plant);

        plantRealmRepository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getName(), NAME));

        // when
        Observable<Integer> result = plantRealmRepository.remove(plant.getId());

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }

    /*
    public void testRemoveOnePlantBySpecification() {

        // setup
        Plant plant = PlantUtils.createPlant(ID, NAME);

        plantRealmRepository.add(plant);

        plantRealmRepository.getById(ID).subscribe(attributeFromDB -> Assert.assertEquals(attributeFromDB.getName(), NAME));

        RealmSpecification realmSpecification = new PlantByNameSpecification(NAME);

        // when
        Observable<Integer> result = plantRealmRepository.remove(realmSpecification);

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }*/
}
