package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class IrrigationRealm extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private Date irrigationDate;

    @Required
    private String gardenId;

    private DoseRealm dose;

    private float quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getIrrigationDate() {
        return irrigationDate;
    }

    public void setIrrigationDate(Date irrigationDate) {
        this.irrigationDate = irrigationDate;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public DoseRealm getDose() {
        return dose;
    }

    public void setDose(DoseRealm dose) {
        this.dose = dose;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
