package com.example.igiagante.thegarden.repository.realm.utils;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class DoseUtils {

    public static Dose createDose(String id) {
        Dose dose = new Dose();
        dose.setId(id);
        dose.setWater(3);
        dose.setPhDose(2);
        dose.setPh((float)6.5);
        dose.setEc((float)1.2);

        Nutrient nutrientOne = NutrientUtils.createNutrient("1", "flora", 6, "3-4-5", "good");
        Nutrient nutrientTwo = NutrientUtils.createNutrient("2", "bloom", (float)6.2, "3-4-5", "good");

        ArrayList<Nutrient> nutrients = new ArrayList<>();
        nutrients.add(nutrientOne);
        nutrients.add(nutrientTwo);

        return dose;
    }
}
