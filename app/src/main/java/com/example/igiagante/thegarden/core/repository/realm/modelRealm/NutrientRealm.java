package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class NutrientRealm extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String name;

    private float ph;

    private String npk;

    private String description;

    private RealmList<ImageRealm> images;

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

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public String getNpk() {
        return npk;
    }

    public void setNpk(String npk) {
        this.npk = npk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<ImageRealm> getImages() {
        return images;
    }

    public void setImages(RealmList<ImageRealm> images) {
        this.images = images;
    }
}
