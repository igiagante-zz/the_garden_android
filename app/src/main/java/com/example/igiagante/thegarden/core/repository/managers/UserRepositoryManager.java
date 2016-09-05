package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.realm.UserRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiGardenRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiNutrientRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserRepositoryManager extends BaseRepositoryManager {

    private UserRealmRepository realmRepository;
    private RestApiGardenRepository api;
    private RestApiNutrientRepository restApiNutrientRepository;

    @Inject
    public UserRepositoryManager(Context context, Session session) {
        super(context);
        realmRepository = new UserRealmRepository(context);
        api = new RestApiGardenRepository(context, session);
        restApiNutrientRepository = new RestApiNutrientRepository(context, session);
    }

    public Observable<Boolean> checkIfUserExistsInDataBase(@Nullable String userId) {
        return realmRepository.getById(userId).flatMap(user -> {
            if (user != null) {
                return Observable.just(true);
            }
            return Observable.just(false);
        });
    }

    public Observable<User> saveUser(@NonNull User user) {
        if (!checkInternet()) {
            return Observable.just(user);
        }
        return realmRepository.add(user);
    }

    public Observable<User> updateUser(@NonNull User user) {
        if (!checkInternet()) {
            return Observable.just(user);
        }
        return realmRepository.update(user);
    }

    /**
     * Return an observable a list of resources.
     *
     * @return Observable
     */
    public Observable query(@Nullable User user) {

        //check if the user has gardens into the database
        Observable<User> query = realmRepository.getById(user.getId());

        List<User> list = new ArrayList<>();
        query.subscribe(userFromDB -> list.add(userFromDB));

        if (!list.isEmpty()) {
            user = list.get(0);
        }

        if (!checkInternet()) {
            return Observable.just(user);
        }

        if(user.getGardens() != null && user.getGardens().isEmpty() && !TextUtils.isEmpty(user.getUserName())){

            //if the user has an empty database, it should ask to the api for the gardens
            Observable<List<Garden>> gardensByUser = api.getGardensByUser(user.getUserName());

            final User userCopy = user;

            gardensByUser.subscribeOn(Schedulers.io()).toBlocking().subscribe(result -> {
                userCopy.setGardens((ArrayList<Garden>) result);
            });

            //if the user has an empty database, it should ask to the api for the nutrients
            Observable<List<Nutrient>> nutrientsByUser = restApiNutrientRepository.getNutrientsByUser(user.getUserName());

            nutrientsByUser.subscribeOn(Schedulers.io()).toBlocking().subscribe(result -> {
                userCopy.setNutrients((ArrayList<Nutrient>) result);
            });

            // update db user with gardens
            realmRepository.update(userCopy);

            return Observable.just(userCopy);

        } else {
            return Observable.just(user);
        }
    }
}
