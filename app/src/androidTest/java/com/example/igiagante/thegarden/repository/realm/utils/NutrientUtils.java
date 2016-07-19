package com.example.igiagante.thegarden.repository.realm.utils;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class NutrientUtils {

    public static Nutrient createNutrient(String id, String name, float ph, String npk, String description) {

        Nutrient nutrient = new Nutrient();
        nutrient.setId(id);
        nutrient.setName(name);
        nutrient.setPh(ph);
        nutrient.setNpk(npk);
        nutrient.setDescription(description);

        return nutrient;
    }

    public static ArrayList<Nutrient> createNutrients() {

        ArrayList<Nutrient> nutrients = new ArrayList<>();

        Nutrient nutrientOne = createNutrient("1", "root", 6, "1-1-1", "nice");
        Nutrient nutrientTwo = createNutrient("2", "veg", 6, "5-2-3", "abius");
        Nutrient nutrientThree = createNutrient("3", "flora", (float)6.5, "1-20-17", "right");

        nutrients.add(nutrientOne);
        nutrients.add(nutrientTwo);
        nutrients.add(nutrientThree);

        return nutrients;
    }

    public static Nutrient createNutrientWithImages() {

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
    public static Image createImage(String id, String name, boolean main) {

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
