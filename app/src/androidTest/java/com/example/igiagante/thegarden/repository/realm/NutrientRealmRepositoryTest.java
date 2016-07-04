package com.example.igiagante.thegarden.repository.realm;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.realm.NutrientRealmRepository;

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
        Nutrient nutrient = createNutrient(ID, NAME, 6, "5-3-4", "nice");
        repository.add(nutrient);

        // verify
        repository.getById(ID).subscribe(
                item -> Assert.assertEquals(item.getName(), NAME)
        );
    }

    public void testPersistNutrients() {

        // setup
        // create three nutrients
        ArrayList<Nutrient> nutrients = createNutrients();

        // when
        Observable<Integer> result = repository.add(nutrients);

        // assertions
        result.subscribe(count -> Assert.assertEquals(3, count.intValue()));
    }

    public void testUpdateOneNutrientRealm() {

        // setup
        final String NEW_NAME = "booster";

        // setup
        Nutrient nutrient = createNutrient(ID, NAME, 6, "5-3-4", "nice");
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
        Nutrient nutrient = createNutrientWithImages();

        // when
        repository.add(nutrient);

        // assertions
        repository.getById(ID).subscribe(nutrientFromDB -> Assert.assertEquals(nutrientFromDB.getImages().size(), 2));
    }

    public void testRemoveOneNutrient() {

        // setup
        Nutrient nutrient = createNutrient(ID, NAME, 6, "5-3-4", "nice");
        repository.add(nutrient);

        repository.getById(ID).subscribe(nutrientFromDB -> Assert.assertEquals(nutrientFromDB.getName(), NAME));

        // when
        Observable<Integer> result = repository.remove(nutrient.getId());

        // assertions
        result.subscribe(count -> Assert.assertEquals(1, count.intValue()));
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

    private ArrayList<Nutrient> createNutrients() {

        ArrayList<Nutrient> nutrients = new ArrayList<>();

        Nutrient nutrientOne = createNutrient("1", "root", 6, "1-1-1", "nice");
        Nutrient nutrientTwo = createNutrient("2", "veg", 6, "5-2-3", "abius");
        Nutrient nutrientThree = createNutrient("3", "flora", (float)6.5, "1-20-17", "right");

        nutrients.add(nutrientOne);
        nutrients.add(nutrientTwo);
        nutrients.add(nutrientThree);

        return nutrients;
    }

    private Nutrient createNutrientWithImages() {

        Nutrient nutrient = createNutrient("1", "root", 6, "1-1-1", "nice");

        ArrayList<Image> images = new ArrayList<>();

        Image imageOne = createImage("1", "mango", true);
        Image imageTwo = createImage("2", "naranja", false);

        images.add(imageOne);
        images.add(imageTwo);

        nutrient.setImages(images);

        return nutrient;
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
