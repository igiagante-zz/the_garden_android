package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserRealm extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String username;

    private RealmList<GardenRealm> gardens;

    private RealmList<NutrientRealm> nutrients;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RealmList<GardenRealm> getGardens() {
        return gardens;
    }

    public void setGardens(RealmList<GardenRealm> gardens) {
        this.gardens = gardens;
    }

    public RealmList<NutrientRealm> getNutrients() {
        return nutrients;
    }

    public void setNutrients(RealmList<NutrientRealm> nutrients) {
        this.nutrients = nutrients;
    }
}
