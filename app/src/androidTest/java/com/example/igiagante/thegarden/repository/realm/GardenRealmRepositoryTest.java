package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.GardenSpecification;

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
        Garden garden = createGarden(ID, NAME, new Date(), DATE);
        repository.add(garden);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testGetAll() {

        // setup
        Garden garden = createGardenWithPlants();

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
        ArrayList<Garden> gardens = createGardens();

        // when
        Observable<Integer> result = repository.add(gardens);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testPersistOneGardenWithPlants() {

        // setup
        Garden garden = createGardenWithPlants();

        // when
        Observable<Garden> result = repository.add(garden);

        // assertions
        result.subscribe(garden1 -> Assert.assertEquals(2, garden1.getPlants().size()));
    }

    public void testUpdateOneGardenRealm() {

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

    private Garden createGardenWithPlants() {
        ArrayList<Plant> plants = createPlantsWithImages();
        Garden garden = createGarden(ID, NAME, new Date(), DATE);
        garden.setPlants(plants);
        return garden;
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
     * Create one plant with images
     *
     * @param id   Id
     * @param name Plant's name
     * @return plant
     */
    private Plant createPlantWithImages(String id, String name) {

        Plant plant = createPlant(id, name);

        ArrayList<Image> images = new ArrayList<>();

        Image imageOne = createImage("1", "mango", true);
        Image imageTwo = createImage("2", "naranja", false);

        images.add(imageOne);
        images.add(imageTwo);

        plant.setImages(images);

        return plant;
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
        plant.setFloweringTime("7 weeks");
        plant.setSeedDate(new Date());
        plant.setPhSoil(6);
        plant.setEcSoil(1);
        plant.setHarvest(60);
        plant.setDescription("Description");

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
        image.setThumbnailUrl("thumbnailUrl");
        image.setType("jpeg");
        image.setSize(4233);
        image.setMain(main);

        return image;
    }
}
