package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class AttributeRealm extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String name;

    @Required
    private String type;

    private int percentage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
