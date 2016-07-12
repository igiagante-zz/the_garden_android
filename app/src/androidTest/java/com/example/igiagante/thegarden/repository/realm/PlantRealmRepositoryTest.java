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
    private final String TYPE = "effects";

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
        attributeRealmRepository.add(createAttributes());
        plagueRealmRepository.add(createPlagues());

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
        Plant plant = createPlant(ID, NAME);
        plantRealmRepository.add(plant);

        // verify
        plantRealmRepository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistOnePlant() {

        Plant plant = createPlant(ID, NAME);

        plantRealmRepository.add(plant);

        plantRealmRepository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getName(), NAME));
    }

    /**
     * Test add(final Iterable<Plant> plants)
     */
    public void testPersistPlants() {

        // setup
        // create three plants
        ArrayList<Plant> plants = createPlants();

        // when
        Observable<Integer> result = plantRealmRepository.add(plants);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testUpdateOnePlantRealm() {

        // setup
        final String NEW_NAME = "mango2";

        Plant plant = createPlant(ID, NAME);

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
        Plant plant = createPlantWithImages(ID, NAME);

        // when
        plantRealmRepository.add(plant);

        // assertions
        plantRealmRepository.getById(ID).subscribe(attributeFromDB -> Assert.assertEquals(attributeFromDB.getImages().size(), 2));
    }

    public void testPersistPlantsWithImages() {

        // setup
        // create two plants with two images each one
        ArrayList<Plant> plants = createPlantsWithImages();

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
        Plant plant = createPlantWithAll(ID, NAME);

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
        Plant plant = createPlantWithAll(ID, NAME);

        Plant plantUpdated = createPlantWithAllForUpdate(ID, NAME);

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
        Plant plant = createPlant(ID, NAME);

        plantRealmRepository.add(plant);

        plantRealmRepository.getById(ID).subscribe(plantFromDB -> Assert.assertEquals(plantFromDB.getName(), NAME));

        // when
        Observable<Integer> result = plantRealmRepository.remove(plant.getId());

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
    }

    public void testRemoveOnePlantBySpecification() {

        // setup
        Plant plant = createPlant(ID, NAME);

        plantRealmRepository.add(plant);

        plantRealmRepository.getById(ID).subscribe(attributeFromDB -> Assert.assertEquals(attributeFromDB.getName(), NAME));

        RealmSpecification realmSpecification = new PlantByNameSpecification(NAME);

        // when
        Observable<Integer> result = plantRealmRepository.remove(realmSpecification);

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
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
        plant.setFloweringTime("7 weeks");
        plant.setSeedDate(new Date());
        plant.setPhSoil(6);
        plant.setEcSoil(1);
        plant.setHarvest(60);
        plant.setDescription("Description");

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

        Plant plant = createPlant(id, name);

        ArrayList<Image> images = new ArrayList<>();

        Image imageOne = createImage("1", "mango", true);
        Image imageTwo = createImage("2", "naranja", false);

        images.add(imageOne);
        images.add(imageTwo);

        plant.setImages(images);

        return plant;
    }

    private Plant createPlantWithAll(String id, String name) {

        Plant plant = createPlantWithImages(id, name);

        // ADD FLAVORS
        plant.setFlavors(createFlavors());

        // ADD ATTRIBUTES
        plant.setAttributes(createAttributes());

        // ADD PLAGUES
        plant.setPlagues(createPlagues());

        return plant;
    }

    private Plant createPlantWithAllForUpdate(String id, String name) {

        Plant plant = createPlantWithImages(id, name);

        // UPDATE FLAVORS
        ArrayList<Flavor> flavors = new ArrayList<>();

        Flavor flavorOne = createFlavor("1", "lemon", "/images/flavors/lemon");
        Flavor flavorThree = createFlavor("3", "soil", "/images/flavors/soil");

        flavors.add(flavorOne);
        flavors.add(flavorThree);

        plant.setFlavors(flavors);

        // UPDATE ATTRIBUTES
        ArrayList<Attribute> attributes = new ArrayList<>();

        Attribute attributeOne = createAttribute("1", "Euphoric", "effects", 50);
        Attribute attributeThree = createAttribute("3", "Symptoms", "headache", 60);

        attributes.add(attributeOne);
        attributes.add(attributeThree);

        plant.setAttributes(attributes);

        // UPDATE PLAGUES
        ArrayList<Plague> plagues = new ArrayList<>();

        Plague plagueOne = createPlague("1", "caterpillar", "/images/flavors/caterpillar");
        Plague PlagueTwo = createPlague("2", "trip", "/images/flavors/trip");
        Plague PlagueThree = createPlague("4", "fly_white", "/images/flavors/fly_white");

        plagues.add(plagueOne);
        plagues.add(PlagueTwo);
        plagues.add(PlagueThree);

        plant.setPlagues(plagues);

        return plant;
    }

    /**
     * Create a list of attributes
     *
     */
    private ArrayList<Attribute> createAttributes() {

        ArrayList<Attribute> attributes = new ArrayList<>();

        Attribute attributeOne = createAttribute("1", "Euphoric", "effects", 30);
        Attribute attributeTwo = createAttribute("2", "Insomnia", "medicinal", 50);
        Attribute attributeThree = createAttribute("3", "Symptoms", "headache", 70);

        attributes.add(attributeOne);
        attributes.add(attributeTwo);
        attributes.add(attributeThree);

        return attributes;
    }

    /**
     * Create a list of plagues
     *
     * @return plagues
     */
    private ArrayList<Plague> createPlagues() {

        ArrayList<Plague> plagues = new ArrayList<>();

        Plague plagueOne = createPlague("1", "caterpillar", "/image/plagues/caterpillar.jpg");
        Plague plagueTwo = createPlague("2", "trip", "/image/plagues/trip.jpg");
        Plague plagueThree = createPlague("3", "spider", "/image/plagues/spider.jpg");
        Plague plagueFour = createPlague("4", "fly_white", "/image/plagues/fly_white.jpg");

        plagues.add(plagueOne);
        plagues.add(plagueTwo);
        plagues.add(plagueThree);
        plagues.add(plagueFour);

        return plagues;
    }

    /**
     * Create a list of flavors
     *
     * @return flavors
     */
    private ArrayList<Flavor> createFlavors() {

        ArrayList<Flavor> flavors = new ArrayList<>();

        Flavor flavorOne = createFlavor("1", "lemon", "/images/flavors/lemon");
        Flavor flavorTwo = createFlavor("2", "wood", "/images/flavors/wood");
        Flavor flavorThree = createFlavor("3", "soil", "/images/flavors/soil");

        flavors.add(flavorOne);
        flavors.add(flavorTwo);
        flavors.add(flavorThree);

        return flavors;
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

    private Flavor createFlavor(String id, String name, String imageUrl) {
        Flavor flavor = new Flavor();
        flavor.setId(id);
        flavor.setName(name);
        flavor.setImageUrl(imageUrl);
        return flavor;
    }

    private Attribute createAttribute(String id, String name, String type, int percentage) {
        Attribute attribute = new Attribute();
        attribute.setId(id);
        attribute.setName(name);
        attribute.setType(type);
        attribute.setPercentage(percentage);
        return attribute;
    }

    private Plague createPlague(String id, String name, String imageUrl) {
        Plague plague = new Plague();
        plague.setId(id);
        plague.setName(name);
        plague.setImageUrl(imageUrl);
        return plague;
    }

}
