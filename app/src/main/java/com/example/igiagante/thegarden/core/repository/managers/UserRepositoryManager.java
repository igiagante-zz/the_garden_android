package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.UserRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.specification.UserByNameSpecification;
import com.example.igiagante.thegarden.core.repository.restAPI.repositories.RestApiGardenRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ignacio Giagante, on 5/8/16.
 */
public class UserRepositoryManager {

    private UserRealmRepository realmRepository;
    private RestApiGardenRepository api;
    private Context context;

    @Inject
    public UserRepositoryManager(Context context, Session session) {
        realmRepository = new UserRealmRepository(context);
        api = new RestApiGardenRepository(context, session);
        this.context = context;
    }

    public Observable<Boolean> checkIfUserExistsInDataBase(@Nullable String userId){
        return realmRepository.getById(userId).flatMap(user -> {
            if(user != null){
                return Observable.just(true);
            }
            return Observable.just(false);
        });
    }

    public Observable<User> saveUser(@NonNull User user) {
        return  realmRepository.add(user);
    }

    /**
     * Return an observable a list of resources.
     * @return Observable
     */
    public Observable query(@Nullable String userId) {

        //check if the user has gardens into the database
        Observable<User> query = realmRepository.getById(userId);

        List<User> list = new ArrayList<>();
        query.subscribe(user -> list.add(user));

        User user = new User();
        if(!list.isEmpty()) {
            user = list.get(0);
            Log.d("User added", " username --- " + user.getUserName());
        }

        if(user.getGardens() != null && !TextUtils.isEmpty(user.getUserName())){

            //if the user has an empty database, it should ask to the api for the gardens
            Observable<List<Garden>> gardensByUser = api.getGardensByUser(user.getUserName());

            final User userCopy = user;

            gardensByUser.subscribeOn(Schedulers.io()).toBlocking().subscribe(result -> {
                userCopy.setGardens((ArrayList<Garden>) result);
            });

            // update db user with gardens
            realmRepository.update(userCopy);

            return Observable.just(userCopy);

        } else {
            return Observable.just(user);
        }
    }
}
