package com.example.igiagante.thegarden.repository;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.igiagante.thegarden.core.repository.RealmSpecification;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.plants.domain.entity.Image;
import com.example.igiagante.thegarden.plants.domain.entity.Plant;
import com.example.igiagante.thegarden.plants.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.plants.repository.realm.specification.PlantByIdSpecification;
import com.example.igiagante.thegarden.plants.repository.realm.specification.PlantByNameSpecification;
import com.example.igiagante.thegarden.plants.repository.realm.specification.PlantSpecification;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by igiagante on 28/4/16.
 */
public class PlantRealmRepositoryTest extends AndroidTestCase {

    private static final String TAG = PlantRealmRepositoryTest.class.getSimpleName();

    private PlantRealmRepository repository;
    private final String ID = "1";
    private final String NAME = "mango";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new PlantRealmRepository(getContext());
        repository.removeAll();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.removeAll();
    }

    public void testGetById() {

        // setup
        Plant plant = createPlant(ID, NAME);
        repository.add(plant);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistOnePlant() {

        Plant plant = createPlant(ID, NAME);

        repository.add(plant);

        repository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getName(), NAME));
    }

    /**
     * Test add(final Iterable<Plant> plants)
     */
    public void testPersistPlants() {

        // setup
        // create three plants
        ArrayList<Plant> plants = createPlants();

        // when
        int count = repository.add(plants);

        // assertions
        Assert.assertEquals(3, count);
    }

    public void testUpdateOnePlantRealm() {

        // setup
        final String NEW_NAME = "mango2";

        Plant plant = createPlant(ID, NAME);

        repository.add(plant);

        // when
        plant.setName(NEW_NAME);
        repository.update(plant);

        // assertions
        repository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(NEW_NAME, plantFromDB.getName()));
    }

    public void testPersistOnePlantWithImages() {

        // setup
        // create plant with two images
        Plant plant = createPlantWithImages(ID, NAME);

        // when
        repository.add(plant);

        // assertions
        repository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getImages().size(), 2));
    }

    public void testPersistPlantsWithImages() {

        // setup
        // create two plants with two images each one
        ArrayList<Plant> plants = createPlantsWithImages();

        // when
        int count = repository.add(plants);

        // assertions
        Assert.assertEquals(2, count);

        repository.query(new PlantSpecification()).subscribe(
                plantFromDB -> Assert.assertEquals(2, plantFromDB.get(0).getImages().size())
        );
    }

    public void testRemoveOnePlant() {

        // setup
        Plant plant = createPlant(ID, NAME);

        repository.add(plant);

        repository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getName(), NAME));

        // when
        int result = repository.remove(plant);

        // assertions
        Assert.assertEquals(1, result);
    }

    public void testRemoveOnePlantBySpecification() {

        // setup
        Plant plant = createPlant(ID, NAME);

        repository.add(plant);

        repository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getName(), NAME));

        RealmSpecification realmSpecification = new PlantByNameSpecification(NAME);

        // when
        int result = repository.remove(realmSpecification);

        // assertions
        Assert.assertEquals(1, result);
    }


    /**
     * Create a list of plants without images
     *
     * @return plants
     */
    private ArrayList<Plant> createPlants() {

        ArrayList<Plant> plants = new ArrayList<>();

        Plant plantOne = createPlant("1", "mango");
        Plant plantTwo = createPlant("2", "pera");
        Plant plantThree = createPlant("3", "naranja");

        plants.add(plantOne);
        plants.add(plantTwo);
        plants.add(plantThree);

        return plants;
    }

    /**
     * Create a list of two plants with two images each one
     * @return plants
     */
    private ArrayList<Plant> createPlantsWithImages() {

        ArrayList<Plant> plants = new ArrayList<>();

        // plant one
        Plant plantOne = createPlantWithImages("1", "mango");

        Image imageOne = createImage("1", "mango", true);
        Image imageTwo = createImage("2", "mango2", false);

        ArrayList<Image> images = new ArrayList<>();
        images.add(imageOne);
        images.add(imageTwo);

        plantOne.setImages(images);

        // plant two
        Plant plantTwo = createPlantWithImages("2", "pera");

        Image imageThree = createImage("3", "pera", true);
        Image imageFour = createImage("4", "pera2", false);

        ArrayList<Image> imagesPlantTwo = new ArrayList<>();
        imagesPlantTwo.add(imageThree);
        imagesPlantTwo.add(imageFour);

        plantTwo.setImages(imagesPlantTwo);

        plants.add(plantOne);
        plants.add(plantTwo);

        return plants;
    }

    /**
     * Create one plant
     *
     * @param id   Id
     * @param name Plant's name
     * @return plant
     */
    private Plant createPlant(String id, String name) {

        Plant plant = new Plant();
        plant.setId(id);
        plant.setName(name);
        plant.setSize(30);
        plant.setGardenId("1");
        plant.setPhSoil(6);
        plant.setEcSoil(1);
        plant.setHarvest(60);

        return plant;
    }

    /**
     * Create one plant with images
     *
     * @param id   Id
     * @param name Plant's name
     * @return plant
     */
    private Plant createPlantWithImages(String id, String name) {

        Plant plant = new Plant();
        plant.setId(id);
        plant.setName(name);
        plant.setSize(30);
        plant.setGardenId("1");
        plant.setPhSoil(6);
        plant.setEcSoil(1);
        plant.setHarvest(60);

        ArrayList<Image> images = new ArrayList<>();

        Image imageOne = createImage("1", "mango", true);
        Image imageTwo = createImage("2", "naranja", false);

        images.add(imageOne);
        images.add(imageTwo);

        plant.setImages(images);

        return plant;
    }

    /**
     * Create one image (domain)
     *
     * @param id   Id
     * @param name Image's name
     * @return image
     */
    private Image createImage(String id, String name, boolean main) {

        Image image = new Image();
        image.setId(id);
        image.setName(name);
        image.setUrl("url");
        image.setThumbnailUrl("thumbUrl");
        image.setType("jpeg");
        image.setSize(4233);
        image.setMain(main);

        return image;
    }
}
