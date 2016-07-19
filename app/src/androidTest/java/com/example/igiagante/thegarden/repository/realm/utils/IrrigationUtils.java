package com.example.igiagante.thegarden.repository.realm.utils;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ignacio Giagante, on 19/7/16.
 */
public class IrrigationUtils {

    public static ArrayList<Irrigation> createIrrigations() {

        ArrayList<Irrigation> irrigations = new ArrayList<>();

        Irrigation irrigationOne = createIrrigation("1");
        Irrigation irrigationTwo = createIrrigation("2");
        Irrigation irrigationThree = createIrrigation("3");

        irrigations.add(irrigationOne);
        irrigations.add(irrigationTwo);
        irrigations.add(irrigationThree);

        return irrigations;
    }

    public static Irrigation createIrrigation(String id) {
        Irrigation irrigation = new Irrigation();
        irrigation.setId(id);
        irrigation.setGardenId("1324");
        irrigation.setQuantity(3);
        irrigation.setIrrigationDate(new Date());
        irrigation.setDose(DoseUtils.createDose(id));

        return irrigation;
    }
}
