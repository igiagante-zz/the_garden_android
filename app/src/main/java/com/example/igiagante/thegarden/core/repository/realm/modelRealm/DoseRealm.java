package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseRealm extends RealmObject {

    @PrimaryKey
    private String id;

    private float water;

    private float phDose;

    private float ec;

    private float ph;

    private RealmList<NutrientPerDoseRealm> nutrients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public float getPhDose() {
        return phDose;
    }

    public void setPhDose(float phDose) {
        this.phDose = phDose;
    }

    public float getEc() {
        return ec;
    }

    public void setEc(float ec) {
        this.ec = ec;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public RealmList<NutrientPerDoseRealm> getNutrients() {
        return nutrients;
    }

    public void setNutrients(RealmList<NutrientPerDoseRealm> nutrients) {
        this.nutrients = nutrients;
    }
}
