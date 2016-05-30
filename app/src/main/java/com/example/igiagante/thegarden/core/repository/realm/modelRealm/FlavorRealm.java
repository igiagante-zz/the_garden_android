package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by igiagante on 26/4/16.
 */
public class FlavorRealm extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String name;

    @Required
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
