package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlantRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.UserRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserToUserRealm implements Mapper<User, UserRealm> {

    private final Realm realm;
    private final GardenToGardenRealm toGardenRealm;

    public UserToUserRealm(Realm realm) {
        this.realm = realm;
        this.toGardenRealm = new GardenToGardenRealm(realm);
    }

    @Override
    public UserRealm map(User user) {
        // create user realm object and set id
        UserRealm userRealm = realm.createObject(UserRealm.class);
        userRealm.setId(user.getId());
        return copy(user, userRealm);
    }

    @Override
    public UserRealm copy(User user, UserRealm userRealm) {

        userRealm.setUsername(user.getUserName());

        ArrayList<Garden> gardens = user.getGardens();
        RealmList<GardenRealm> gardenRealms = new RealmList<>();

        if(gardens != null && !gardens.isEmpty()) {
            for(Garden garden : gardens) {
                GardenRealm gardenRealm = realm.where(GardenRealm.class).equalTo(Table.ID, garden.getId()).findFirst();
                if (gardenRealm == null) {
                    // create garden realm object and set id
                    gardenRealm = realm.createObject(GardenRealm.class);
                    gardenRealm.setId(garden.getId());
                }
                gardenRealms.add(toGardenRealm.copy(garden, gardenRealm));
            }
        }

        userRealm.setGardens(gardenRealms);

        return userRealm;
    }
}
