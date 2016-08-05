package com.example.igiagante.thegarden.core.repository.realm.mapper;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.GardenRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.UserRealm;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserRealmToUser implements Mapper<UserRealm, User> {

    private final GardenRealmToGarden toGarden;

    public UserRealmToUser(Context context) {
        this.toGarden = new GardenRealmToGarden(context);
    }

    @Override
    public User map(UserRealm userRealm) {
        User user = new User();
        user.setId(userRealm.getId());
        return copy(userRealm, user);
    }

    @Override
    public User copy(UserRealm userRealm, User user) {
        user.setUserName(userRealm.getUsername());

        ArrayList<Garden> gardens = new ArrayList<>();

        RealmList<GardenRealm> gardenRealmList = userRealm.getGardens();

        if(gardenRealmList != null && !gardenRealmList.isEmpty()) {
            for(GardenRealm gardenRealm : gardenRealmList){
                gardens.add(toGarden.map(gardenRealm));
            }
        }

        user.setGardens(gardens);

        return user;
    }
}
