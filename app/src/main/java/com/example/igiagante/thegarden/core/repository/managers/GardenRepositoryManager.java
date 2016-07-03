package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.repository.Repository;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRepositoryManager extends RepositoryManager<Repository<Garden>> {

    @Inject
    public GardenRepositoryManager(Context context) {
        mRepositories.add(new GardenRealmRepository(context));
    }
}
