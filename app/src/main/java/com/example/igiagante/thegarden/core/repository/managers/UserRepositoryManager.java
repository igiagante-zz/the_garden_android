package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.Specification;
import com.example.igiagante.thegarden.core.repository.realm.UserRealmRepository;
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

    @Inject
    public UserRepositoryManager(Context context, Session session) {
        realmRepository = new UserRealmRepository(context);
        api = new RestApiGardenRepository(context, session);
    }

    /**
     * Return an observable a list of resources.
     * @param specification {@link Specification}
     * @return Observable
     */
    public Observable query(@Nullable Specification specification, @Nullable String username) {

        //check if the user has gardens into the database
        Observable<List<User>> query = realmRepository.query(specification);

        List<User> list = new ArrayList<>();
        query.subscribe(users -> list.addAll(users));

        if(!list.isEmpty() && list.get(0).getGardens()!= null && !list.get(0).getGardens().isEmpty()){
            return Observable.just(list);
        } else {
            //if the user has an empty database, it should ask to the api for the gardens
            Observable<List<Garden>> gardensByUser = api.getGardensByUser(username);

            gardensByUser.subscribeOn(Schedulers.io()).toBlocking().subscribe(result -> {
                list.get(0).setGardens((ArrayList<Garden>) result);
            });
            return Observable.just(gardensByUser);
        }
    }
}
