package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Ignacio Giagante, on 30/6/16.
 */
public class AttributePerPlantRealm extends RealmObject {

    @PrimaryKey
    private String attributeId;

    @Required
    private Integer percentage;

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
